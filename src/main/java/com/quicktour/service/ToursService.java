package com.quicktour.service;

import com.quicktour.dto.DiscountPoliciesResult;
import com.quicktour.entity.*;
import com.quicktour.repository.CompanyRepository;
import com.quicktour.repository.PriceIncludeRepository;
import com.quicktour.repository.ToursRepository;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class ToursService {
    @Value("${numberOfToursOnPage}")
    private int NUMBER_OF_RECORDS_PER_PAGE;
    @Value("${numberOfFamousTours}")
    private int NUMBER_OF_FAMOUS_TOURS;
    private static final Logger logger = LoggerFactory.getLogger(ToursService.class);
    @Autowired
    PriceIncludeRepository priceIncludeRepository;
    @Autowired
    private UsersService usersService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private DiscountPolicyService discountPolicyService;
    @Autowired
    PhotoService photoService;
    private EntityManager entityManager;
    @Autowired
    private ToursRepository toursRepository;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Tour findOne(Integer id) {
        return toursRepository.findOne(id);
    }

    public List<Tour> saveTours(List<Tour> tours) {
        return toursRepository.save(tours);
    }

    public List<Tour> findTours(Integer[] tourIds) {
        return toursRepository.findByTourIdIn(Arrays.asList(tourIds));
    }

    public Tour saveTour(Tour tour) {
        return toursRepository.saveAndFlush(tour);
    }

    public List<Tour> findAllTours() {
        return toursRepository.findAll();
    }

    public List<Tour> findByCurrentUserAgency() {
        User user = usersService.getCurrentUser();
        if (user.getRole().getRoleId() == Role.ROLE_AGENT) {
            Company company = companyRepository.findByCompanyCode(user.getCompanyCode());
            return (List<Tour>) company.getTours();
        }
        return findAllTours();
    }

    public Page<Tour> findAllTours(int pageNumber) {
        return toursRepository.findByActiveTrue(new PageRequest(pageNumber, NUMBER_OF_RECORDS_PER_PAGE));
    }

    public Page<Tour> findAllTours(int pageNumber, int numberOfRecords) {
        long count = toursRepository.count();
        if (pageNumber * numberOfRecords > count) {
            pageNumber = (int) count / numberOfRecords;
        }
        return toursRepository.findByActiveTrue(new PageRequest(pageNumber, numberOfRecords));
    }

    public Page<Tour> findAllToursAndCalculateDiscount(int pageNumber, Integer numberOfRecords) {
        Page<Tour> tourPage;
        if (numberOfRecords == null) {
            tourPage = findAllTours(pageNumber);
        } else {
            tourPage = findAllTours(pageNumber, numberOfRecords);

        }
        List<Tour> toursList = tourPage.getContent();
        DiscountPoliciesResult discountPoliciesResult;
        User user = usersService.getCurrentUser();
        for (Tour tour : toursList) {
            if (user == null) {
                tour.setDiscount(BigDecimal.ZERO);
            } else {
                discountPoliciesResult = discountPolicyService.calculateDiscount(tour.getDiscountPolicies());
                tour.setDiscount(discountPoliciesResult.getDiscount());
                tour.setDiscountPolicies(discountPoliciesResult.getDiscountPolicies());
            }
        }
        return tourPage;
    }

    public Tour findTourById(int id) {
        return toursRepository.findByTourId(id);
    }

    /**
     * Search tours by country
     *
     * @param country-country to search
     * @param pageNumber      number of page for pagination
     * @return tour page with search results
     */
    public Page<Tour> findToursByCountry(String country, int pageNumber) {
        Page<Tour> tours = toursRepository.findToursByCountry(country,
                new PageRequest(pageNumber, NUMBER_OF_RECORDS_PER_PAGE));
        return tours;
    }


    public Page<Tour> findToursByPlaces(String placeName, int pageNumber) {
        Page<Tour> tours = toursRepository.findToursByPlaceName(placeName,
                new PageRequest(pageNumber, NUMBER_OF_RECORDS_PER_PAGE));
        return tours;
    }


    public Page<Tour> findToursByPrice(int minPrice, int maxPrice, int pageNumber) {
        return toursRepository.findToursByPrice(new BigDecimal(minPrice), new BigDecimal(maxPrice),
                new PageRequest(pageNumber, NUMBER_OF_RECORDS_PER_PAGE));
    }


    /**
     * Search tours by entered by users parameters
     */
    public Page<Tour> extendFilter(String country, String place,
                                   java.sql.Date minDate, java.sql.Date maxDate,
                                   Integer minPrice, Integer maxPrice,
                                   int pageNumber) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("p.country=", country);
        conditions.put("p.name=", place);
        conditions.put("ti.startDate>", minDate);
        conditions.put("ti.startDate<", maxDate);
        //TODO:test
        conditions.put("t.price>=", minPrice);
        conditions.put("t.price<=", maxPrice);
        logger.debug("Condition:{}", conditions);

        String sql = createFindTourSQL(conditions);

        if (sql != null) {
            Query query = entityManager.createQuery(sql.toString());
            List<Tour> results = query.getResultList();
            int totalNumberOfResults = results.size();
            if (totalNumberOfResults == 0) {
                return null;

            }
            Page<Tour> tourPage = new PageImpl<Tour>(results,
                    new PageRequest(pageNumber, NUMBER_OF_RECORDS_PER_PAGE),
                    totalNumberOfResults);
            return tourPage;
        }
        return null;
    }

    private String createFindTourSQL(Map<String, Object> conditions) {
        StringBuilder sql = new StringBuilder("select distinct t from Tour as t inner join t.toursPlaces as p " +
                "inner join t.tourInfo as ti where ");
        boolean prevEmpty = true;
        boolean empty;
        int emptyCount = 0;
        for (String conditionKey : conditions.keySet()) {
            Object value = conditions.get(conditionKey);
            empty = value == null || value.toString().isEmpty();
            if (!prevEmpty && !empty) {
                sql.append(" AND ");
                prevEmpty = true;
            }
            if (empty) {
                emptyCount++;
            } else {
                sql.append(conditionKey + "'" + value + "' ");
            }
            if (prevEmpty) {
                prevEmpty = empty;
            }

        }
        return emptyCount == conditions.size() ? null : sql.toString();
    }

    /**
     * Finds tour's minimal price based on TourInfo's discount value and discount policies
     *
     * @param tour tour for which price will be calculate
     * @return minimal price value
     */
    public BigDecimal findMinPrice(Tour tour) {
        BigDecimal price = tour.getPrice();
        double maxDiscount = findMaxDiscount(tour);
        price = price.subtract(price.multiply(BigDecimal.valueOf(maxDiscount / 100)));
        return price;
    }

    public double findMaxTourDiscount(Tour tour) {
        List<TourInfo> tourByDate = (List<TourInfo>) tour.getTourInfo();
        double maxDiscount = tourByDate.get(0).getDiscount();
        for (TourInfo tourInfo : tourByDate) {
            Double discount = tourInfo.getDiscount().doubleValue();
            if (discount > maxDiscount) {
                maxDiscount = discount;
            }
        }
        return maxDiscount;
    }

    public double findMaxDiscount(Tour tour) {
        BigDecimal maxTourDiscount = new BigDecimal(findMaxTourDiscount(tour));
        DiscountPoliciesResult discountPoliciesResult = discountPolicyService.calculateDiscount(tour.getDiscountPolicies());
        BigDecimal totalDiscount = maxTourDiscount.add(discountPoliciesResult.getDiscount());
        BigDecimal companyDiscount = companyService.getCompanyDiscount(usersService.getCurrentUser());
        if (companyDiscount.doubleValue() > 0) {
            totalDiscount = totalDiscount.add(companyDiscount);
        }
        return totalDiscount.doubleValue();
    }

    public List<Tour> findFamousTours() {
        return toursRepository.findFamousTours(new PageRequest(0, NUMBER_OF_FAMOUS_TOURS)).getContent();
    }

    public List<Tour> findAgencyToursWithEmptyDiscountPolicies() {
        User currentUser = usersService.getCurrentUser();
        Company company = companyService.findByCompanyCode(currentUser.getCompanyCode());
        List<Tour> tours = toursRepository.findByCompanyAndDiscountPoliciesIsEmpty(company);
        prepareTour(false, tours);
        return tours;
    }

    public List<Tour> findAgencyToursWithNotEmptyDiscountPolicies() {
        User currentUser = usersService.getCurrentUser();
        Company company = companyService.findByCompanyCode(currentUser.getCompanyCode());
        List<Tour> tours = toursRepository.findByCompanyAndDiscountPoliciesIsNotEmpty(company);
        prepareTour(true, tours);
        return tours;
    }

    /**
     * @param initializeDiscountPolicies
     * @param tours
     * @return
     */
    public List<Tour> prepareTour(boolean initializeDiscountPolicies, List<Tour> tours) {
        List<Tour> toursResult = new ArrayList<>(tours);
        for (Tour tour : toursResult) {
            tour.setPriceIncludes(null);
            tour.setToursPlaces(null);
            if (initializeDiscountPolicies) {
                tour.getDiscountPolicies().size();
            }
        }
        return toursResult;
    }

    public Tour findTourByIdWithPlaces(int id) {
        Tour tour = findTourById(id);
        tour.getToursPlaces().size();
        return tour;
    }


    /**
     * Toggles active variable in tour
     */
    public void toogleActive(int tourId) {
        Tour tour = toursRepository.findOne(tourId);
        tour.toogleActive();
        toursRepository.saveAndFlush(tour);
    }

    /**
     * Save or update tour with dates, places and price descriptions
     *
     * @param tour      object of class Tour which contains Tour, list of Place,
     *                  list of String which represent Price descriptions, and list of TourInfo
     * @param mainPhoto file which contains main photo of the tour
     */
    public void saveCombinedTours(Tour tour, MultipartFile mainPhoto) {
        int price;
        User currentUser = usersService.getCurrentUser();

        if (!mainPhoto.isEmpty()) {
            tour.setPhoto(null);
            photoService.saveImageAndSet(tour, mainPhoto);
        }
        tour.setCompany(companyService.findByCompanyCode(currentUser.getCompanyCode()));
        photoService.saveImageAndSet(tour, mainPhoto);
        logger.debug("Edited tour photo is {}.Empty {}", tour.getPhoto(), mainPhoto.isEmpty());
        logger.debug("Finished places");
        cleanTourUnsafeHTML(tour);
        logger.debug("Finished places");
        ListIterator<TourInfo> tourInfoListIterator = tour.getTourInfo().listIterator();
        while (tourInfoListIterator.hasNext()) {
            TourInfo tourInfo = tourInfoListIterator.next();
            if (tourInfo == null) {
                tourInfoListIterator.remove();
            } else {
                tourInfo.setTour(tour);

            }
        }
        logger.debug("Finished tourInfos");

        price = calculateTourPrice(tour);
        logger.debug("Finished price");
        tour.setPrice(new BigDecimal(price));
        tour.setDescription(tour.getDescription().trim());
        List<PriceDescription> priceIncludes = tour.getPriceIncludes();
        for (int i = 0; i < priceIncludes.size(); i++) {

            priceIncludes.set(i, priceIncludeRepository.findOne(priceIncludes.get(i).getPriceDescriptionId()));
        }
        tour.setPriceIncludes(priceIncludes);
        logger.debug("Description {}", tour.getDescription().length());
        saveTour(tour);
        logger.debug("Tour {} saved successfully", tour.getTourId());
    }

    public void clearEmptyPlaces(Tour tour) {
        List<Place> toursPlaces = tour.getToursPlaces();
        if (toursPlaces != null) {
            Iterator<Place> placeIterator = toursPlaces.iterator();
            while (placeIterator.hasNext()) {
                Place place = placeIterator.next();
                if (place.getPrice() == null && place.getName() == null) {
                    placeIterator.remove();
                } else {
                    List<Tour> placeTours = place.getTours();
                    if (placeTours == null) {
                        placeTours = new ArrayList<Tour>();
                    }
                    placeTours.add(tour);
                    place.setTours(placeTours);
                }
            }
        }
    }


    private int calculateTourPrice(Tour tour) {
        int price = 0;
        if (tour.getToursPlaces() != null) {
            for (Place place : tour.getToursPlaces()) {
                price += place.getPrice().intValue();
            }
        }
        return price;
    }


    private void cleanTourUnsafeHTML(Tour tour) {
        tour.setName(Jsoup.clean(tour.getName(), Whitelist.basic()));
        tour.setDescription(Jsoup.clean(tour.getDescription(), Whitelist.basic()));
        tour.setTransportDesc(Jsoup.clean(tour.getTransportDesc(), Whitelist.basic()));
        List<Place> places = tour.getToursPlaces();
        if (places != null) {
            for (Place place : places) {
                place.setName(Jsoup.clean(place.getName(), Whitelist.basic()));
                place.setDescription(Jsoup.clean(place.getDescription(), Whitelist.basic()));
            }
        }

    }


}
