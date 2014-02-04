package com.quicktour.service;

import com.quicktour.dto.DiscountPoliciesResult;
import com.quicktour.entity.*;
import com.quicktour.repository.DiscountPolicyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

/**
 * @author Roman Lukash
 */
@Service
@Transactional
public class DiscountPolicyService {
    private static final String ZERO = "0";
    private static final String ORDERS = "orders";
    private static final String AND = "AND";
    private static final String CONTAINS = "contains";
    private static final String OR = "OR";
    private static final String DB_NULL = "NULL";

    final Logger logger = LoggerFactory.getLogger(DiscountPolicyService.class);
    @Autowired
    DiscountPolicyRepository discountPoliciesRepository;
    @Autowired
    UsersService usersService;
    @Autowired
    DiscountDependencyService discountDependencyService;
    @Autowired
    CompanyService companyService;
    @Autowired
    ToursService toursService;
    EntityManager entityManager;


    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Saves discount policy in DB
     *
     * @param discountPolicy discount policy to save
     * @return discount policy with extra properties i.e. ID ...
     */
    public DiscountPolicy addDiscountPolicy(DiscountPolicy discountPolicy) {
        User user = usersService.getCurrentUser();
        discountPolicy.setCompany(companyService.getCompanyByUserId(user.getUserId()));
        String convertedFormula = discountDependencyService.convertFormula(discountPolicy.getFormula());
        discountPolicy.setFormula(convertedFormula);
        return discountPoliciesRepository.saveAndFlush(discountPolicy);
    }

    /**
     * Transforms DiscountPolicy's formula.
     *
     * @return amount of discount policy in %
     */
    private BigDecimal calculateDiscountPolicy(String formula) {
        if (formula.contains("/0") || formula.contains(ORDERS)) {
            return BigDecimal.ZERO;
        }
        String discount = ZERO;
        User user = usersService.getCurrentUser();
        String sql = "SELECT " + formula + " from users  WHERE users.user_id=" + user.getUserId();
        logger.debug("formula sql {}", sql);
        Query query = entityManager.createNativeQuery(sql);
        Object result = query.getResultList().get(0);
        if (result != null) {
            discount = result.toString();
        }
        logger.debug("Formula={}", discount);
        return new BigDecimal(discount);
    }


    /**
     * Tests if formula has correct syntax
     */
    public boolean formulaIsNotValid(String formula) {
        logger.debug("Test formula: {}", formula);
        String sql = "SELECT " + formula;
        Query query = entityManager.createNativeQuery(sql);
        try {
            query.getResultList();
        } catch (PersistenceException e) {
            return true;
        }
        return false;
    }


    /**
     * Calculates tour discount based on tour's discount policies
     */
    public DiscountPoliciesResult calculateDiscount(List<DiscountPolicy> discountPolicies) {
        BigDecimal discount = BigDecimal.ZERO;
        User user = usersService.getCurrentUser();
        if (user == null) {
            return new DiscountPoliciesResult(discount, null);
        }
        Date currentDate = new Date(System.currentTimeMillis());
        List<DiscountPolicy> policies = new ArrayList<DiscountPolicy>(discountPolicies);
        for (DiscountPolicy discountPolicy : policies) {
            String condition = discountPolicy.getCondition();
            String formula = discountPolicy.getFormula();
            if (condition != null) {
                Date startDate = discountPolicy.getStartDate();
                Date endDate = discountPolicy.getEndDate();
                if ((startDate != null && (currentDate.before(startDate) || currentDate.after(endDate))) || condition.contains(ORDERS) || formula.contains(ORDERS)) {
                    continue;
                }
                //check condition
                String sql = "SELECT 1 from users WHERE users.user_id=" + user.getUserId() + " AND (" + condition + ")";
                logger.debug("Condition {}", sql);
                Query query = entityManager.createNativeQuery(sql);
                if (query.getResultList().size() == 0) {
                    continue;
                }
            }
            discountPolicy.setActive(true);

            //check if discount policy's formula has tags or operators and calculate policy's discount
            Pattern pattern = Pattern.compile("[a-zA-z*/+-]");
            if (pattern.matcher(formula).find()) {
                BigDecimal calculatedDiscount = calculateDiscountPolicy(formula);
                discountPolicy.setFormula(calculatedDiscount.toString());
                discount = discount.add(calculatedDiscount);
            } else {
                discount = discount.add(new BigDecimal(formula));
            }

            logger.debug("Add to discount {}", formula);

        }
        return new DiscountPoliciesResult(discount, discountPolicies);
    }

    /**
     * Deletes discount policy
     */
    public void delete(Integer id) {
        User currentUser = usersService.getCurrentUser();
        if (currentUser != null) {
            logger.info("Delete discount policy with id {} by user {}", id, currentUser.getLogin());
        }
        if (id != null) {
            discountPoliciesRepository.delete(discountPoliciesRepository.findOne(id));
        }
    }

    /**
     * Finds discount policies by current user's company
     */
    public List<DiscountPolicy> findByCompany() {
        Company company = companyService.getCompanyByUserId(usersService.getCurrentUser().getUserId());
        List<DiscountPolicy> discountPolicies = discountPoliciesRepository.findByCompany(company);
        return changeView(discountPolicies);
    }

    /**
     * Changes discount policy view to user friendly
     */
    private List<DiscountPolicy> changeView(List<DiscountPolicy> discountPolicies) {
        ArrayList<DiscountPolicy> policies = new ArrayList<DiscountPolicy>(discountPolicies);
        for (DiscountPolicy discountPolicy : policies) {
            String condition = discountPolicy.getCondition();
            if (condition != null) {
                discountPolicy.setCondition(condition.replace("users.", "User's ").replace("orders.", "Order's ").
                        replace("LIKE", "CONTAINS").replace("%", "").replace("'", "").replace("WEEKDAY(NOW())", DiscountPolicy.DAYOFWEEK));
            }
            String formula = discountPolicy.getFormula();
            if (formula.matches(".*[a-zA-Z].*")) {
                discountPolicy.setFormula(discountDependencyService.convertFormulaReverse(formula));
            }
        }
        return policies;
    }

    /**
     * Constructs condition,sets it in policy and saves discount policy in DB
     *
     * @param relations  relations between conditions i.e. "AND" or "OR"
     * @param conditions conditions.For example "users.age","users.name".
     * @param signs      operators between condition and parameter.For example ">","<","="
     * @param params     user's input
     */

    public DiscountPolicy addDiscountPolicy(DiscountPolicy discountPolicy, String[] relations, String[] conditions, String[] signs, String[] params) {
        String condition = constructCondition(relations, conditions, signs, params);
        discountPolicy.setCondition(condition);
        return addDiscountPolicy(discountPolicy);
    }

    /**
     * Creates {@link com.quicktour.entity.DiscountPolicy} condition
     *
     * @param relations  relations between conditions i.e. "AND" or "OR"
     * @param conditions conditions.For example "users.age","users.name".
     * @param signs      operators between condition and parameter.For example ">","<","="
     * @param params     user input
     */
    private String constructCondition(String[] relations, String[] conditions, String[] signs, String[] params) {
        int j = 0;
        int m = 0;
        boolean containsAnd = false;
        StringBuilder condition = new StringBuilder();
        for (int i = 0; i < conditions.length; i++) {
            if (i > 0) {
                if (AND.equals(relations[i - 1])) {
                    condition.append(" AND ");
                    containsAnd = true;
                } else {
                    if (containsAnd) {
                        condition = condition.insert(m, "(");
                        condition.append(")");
                        containsAnd = false;
                    }
                    condition.append(" OR ");
                    m = condition.length();
                }
            }
            switch (conditions[i]) {
                case DiscountPolicy.DAYOFWEEK:
                    condition.append("WEEKDAY(NOW())=").append(params[i]);
                    continue;
                case DiscountPolicy.USERS_SEX:
                    condition.append(conditions[i]).append("=");
                    break;
                default:
                    condition.append(conditions[i]);
                    if (CONTAINS.equals(signs[j].toLowerCase())) {
                        condition.append(" LIKE '%").append(params[i]).append("%' ");
                        j++;
                        continue;
                    } else {
                        condition.append(signs[j]);
                        j++;
                    }
                    break;
            }
            condition.append("'").append(params[i]).append("'");
        }
        if (containsAnd && condition.toString().contains(OR)) {
            condition = condition.insert(m, "(");
            condition.append(")");
        }

        logger.debug("Created condition {}", condition);
        return condition.toString();
    }

    /**
     * Connects tours to discount policies and saves them in DB
     *
     * @param tourIds   ids of tour to be connected
     * @param policyIds ids of discount policies to be connected
     * @return saved tours
     */
    public List<Tour> applyDiscount(Integer[] tourIds, Integer[] policyIds) {
        List<Tour> tours = toursService.getTours(tourIds);
        List<DiscountPolicy> discountPolicies = getDiscountPolicies(policyIds);
        logger.info("Applying discount policies {} to tours {}", tours, discountPolicies);
        for (Tour tour : tours) {
            tour.setDiscountPolicies(discountPolicies);
        }
        return toursService.prepareTour(false, toursService.saveTours(tours));
    }

    /**
     * Retrieves a list of discount policies
     *
     * @param policyIds ids of discount policy
     */
    private List<DiscountPolicy> getDiscountPolicies(Integer[] policyIds) {
        return discountPoliciesRepository.findByDiscountPolicyIdIn(Arrays.asList(policyIds));
    }


    /**
     * Removes discount policies from list whose condition is false or  formula==0 or formula
     * contains {@link com.quicktour.entity.Order}  information
     */
    public List<DiscountPolicy> getActivePolicies(List<DiscountPolicy> policies) {
        ArrayList<DiscountPolicy> discountPolicies = new ArrayList<DiscountPolicy>(policies);
        for (ListIterator<DiscountPolicy> iterator = discountPolicies.listIterator(); iterator.hasNext(); ) {
            DiscountPolicy discountPolicy = iterator.next();
            String formula = discountPolicy.getFormula();

            if (!discountPolicy.isActive() || ZERO.equals(formula) || formula.contains(ORDERS)) {
                logger.debug("Delete discount policy {} because {} {} {}", discountPolicy.getName(), !discountPolicy.isActive(), ZERO.equals(formula), formula.contains(ORDERS));
                iterator.remove();
            }
        }
        return discountPolicies;
    }


    public DiscountPoliciesResult calculateDiscount(List<DiscountPolicy> discountPolicies, Order order) {
        if (usersService.getCurrentUser() == null) {
            return new DiscountPoliciesResult(BigDecimal.ZERO, new ArrayList<DiscountPolicy>());
        }
        ArrayList<DiscountPolicy> policies = new ArrayList<DiscountPolicy>(discountPolicies);
        for (DiscountPolicy discountPolicy : policies) {
            String replacedFormula = replaceOrderInFormula(discountPolicy.getFormula(), order);
            discountPolicy.setFormula(replacedFormula);
            String condition = discountPolicy.getCondition();
            if (condition != null) {
                String replacedCondition = replaceOrderInCondition(condition, order);
                discountPolicy.setCondition(replacedCondition);
            }
        }
        DiscountPoliciesResult discountPoliciesResult = calculateDiscount(policies);
        logger.debug("Discount policies :{}", discountPoliciesResult.getDiscountPolicies());
        discountPoliciesResult.setDiscountPolicies(getActivePolicies(policies));

        return discountPoliciesResult;
    }

    private String replaceOrder(String toReplace, Order order) {
        String result = toReplace;
        if (toReplace.contains(ORDERS)) {
            for (Field f : Order.class.getDeclaredFields()) {
                String propertyName = f.getName();
                f.setAccessible(true);
                Object o = null;
                try {
                    o = f.get(order);
                } catch (IllegalAccessException e) {
                    logger.error("Cant access  property {} in Order.{}", propertyName, e);
                }
                if (o == null) {
                    o = DB_NULL;
                }
                result = result.replace(ORDERS + "." + propertyName, o.toString());
            }
        }
        return result;
    }

    private String replaceOrderInFormula(String formula, Order order) {
        return replaceOrder(formula, order);
    }

    private String replaceOrderInCondition(String condition, Order order) {
        return replaceOrder(condition, order);
    }

}

