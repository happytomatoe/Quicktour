package com.quicktour.service;

import com.quicktour.entity.DiscountDependency;
import com.quicktour.repository.DiscountDependencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Roman Lukash
 */

@Service
@Transactional
public class DiscountDependencyService {
    @Autowired
    DiscountDependencyRepository discountDependenciesRepository;
    @Autowired
    OrdersService ordersService;
    @Autowired
    UsersService usersService;
    @Value("${dbname}")
    private String dbName;
    private static final Logger logger = LoggerFactory.getLogger(DiscountDependencyService.class);

    public List<DiscountDependency> findAllDependencies() {
        return discountDependenciesRepository.findAll();
    }

    public DiscountDependency saveAndFlush(DiscountDependency discountPolicy) {
        return discountDependenciesRepository.saveAndFlush(discountPolicy);
    }

    public String[] findAllTags() {
        List<DiscountDependency> dependencies = findAllDependencies();
        String tags[] = new String[dependencies.size()];
        for (int i = 0; i < dependencies.size(); i++) {
            tags[i] = dependencies.get(i).getTag();
        }
        return tags;
    }

    public void delete(DiscountDependency discountDependency) {
        discountDependenciesRepository.delete(discountDependency);
    }

    /**
     * Replaces tags in formula to {@link com.quicktour.entity.DiscountDependency} tableField
     */
    public String convertFormula(String formula) {
        String formulaView = formula;
        List<DiscountDependency> discountDependencies = findAllDependencies();
        for (DiscountDependency discountDependency : discountDependencies) {
            formulaView = formulaView.replace(discountDependency.getTag(), discountDependency.getTableField());
        }
        return formulaView;
    }

    /**
     * Replaces {@link com.quicktour.entity.DiscountDependency} tableField  in formula to tags
     */
    public String convertFormulaReverse(String formula) {
        String formulaView = formula;
        for (DiscountDependency discountDependency : findAllDependencies()) {
            formulaView = formula.replace(discountDependency.getTableField(), discountDependency.getTag());
        }
        return formulaView;
    }

    /**
     * Finds columns in user and order tables whose type is all types of int,double,real,float,decimal
     */
    public List<String> findAvailableColumns() {
        logger.debug("DBname {}", dbName);
        return discountDependenciesRepository.getNumberTypeColumnNames(dbName);
    }
}
