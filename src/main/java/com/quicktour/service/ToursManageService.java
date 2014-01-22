package com.quicktour.service;

import com.quicktour.entity.*;
import com.quicktour.repository.ToursRepository;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ToursManageService {

    final Logger logger = org.slf4j.LoggerFactory.getLogger(ToursManageService.class);

    @Autowired
    private PriceIncludeService priceIncludeService;

    @Autowired
    private ToursService toursService;

    @Autowired
    private TourService tourService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    UsersService usersService;

    @Autowired
    CompanyService companyService;

    @Autowired
    ToursRepository toursRepository;

    @Value("${webRootPath}")
    private String webRootPath;

    /**
     * change tour active state to state preset in activeState parameter
     *
     * @param tourId
     * @param activeState
     * @return true if success
     */
    public boolean changeActiveStage(int tourId, boolean activeState) {
        Tour tour = toursRepository.findOne(tourId);
        tour.setActive(activeState);
        tour = toursRepository.saveAndFlush(tour);
        if (tour.getActive() == activeState) {
            logger.debug("Change tour " + tour.getTourId() + " active state to " + activeState);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Save or update tour with dates, places and price descriptions
     *
     * @param completeTourInfo object of class CompleteTourInfo which contains Tour, list of Place,
     *                         list of String which represent Price descriptions, and list of TourInfo
     * @param mainPhoto file which contains main photo of the tour
     */
    public void saveCombineTours(CompleteTourInfo completeTourInfo, MultipartFile mainPhoto) {
        int price = 0;

        cleanTourUnsafeHTML(completeTourInfo.getTour());
        Tour tour = saveTour(completeTourInfo);
        tour.setMainPhotoUrl(saveImage(tour.getTourId() + ".jpg", mainPhoto));
        toursService.saveTour(tour);

        for (TourInfo tourInfo: completeTourInfo.getTourInfo()){
            tourInfo.setTour(tour);
        }

        removeEmptyPlaces(completeTourInfo);
        cleanPlacesUnsafeHTML(completeTourInfo.getPlaces());
        setTourIdForPlaces(completeTourInfo);
        price = calculateTourPrice(completeTourInfo);
        checkTourInfo(completeTourInfo);

        tour.setTourInfo(completeTourInfo.getTourInfo());
        tour.setToursPlaces(completeTourInfo.getPlaces());
        tour.setPrice(new BigDecimal(price));
        tourService.saveTour(completeTourInfo.getTourInfo());
        placeService.savePlases(completeTourInfo.getPlaces());
        toursService.saveTour(tour);
        logger.debug("Tour " + tour.getTourId() + "saved successfully");
    }

    private String saveImage(String filename, MultipartFile image) {
        if (image.isEmpty()){
            return null;
        }
        try{
            File file = new File(webRootPath + filename);
            FileUtils.writeByteArrayToFile(file, image.getBytes());

        } catch (IOException io){
            return null;
        }  catch (NullPointerException npe){
            return null;
        }
        return filename;
    }

    private void setTourIdForPlaces(CompleteTourInfo completeTourInfo) {
        if (completeTourInfo.getPlaces() != null) {
            for (int i = 0; i < completeTourInfo.getPlaces().size(); i++) {
                List<Tour> tourTemp = new ArrayList<Tour>();
                tourTemp.add(completeTourInfo.getTour());
                completeTourInfo.getPlaces().get(i).setTours(tourTemp);
            }
        }
    }

    private int calculateTourPrice(CompleteTourInfo completeTourInfo) {
        int price = 0;
        if (completeTourInfo.getPlaces() != null) {
            for (Place place: completeTourInfo.getPlaces()) {
                price += Integer.decode(place.getPrice());
            }
        }
        return price;
    }

    private Tour saveTour(CompleteTourInfo completeTourInfo) {
        Tour tour = completeTourInfo.getTour();
        List<String> priceIncludesDesc = completeTourInfo.getPriceIncludes();
        List<PriceDescription> priceDescriptionList = new ArrayList<PriceDescription>();

        tour.setCompany(getCompanyByCurentUser());

        if (priceIncludesDesc != null) {
            for (String desc: priceIncludesDesc) {
                priceDescriptionList.add(priceIncludeService.findByDescription(desc));
            }
            tour.setPriceIncludes(priceDescriptionList);
        }

        return toursService.saveTour(tour);
    }

    private Company getCompanyByCurentUser() {
        User agent = usersService.getCurrentUser();
        String companyCode = agent.getCompanyCode();
        return companyService.findByCompanyCode(companyCode);
    }

    private void removeEmptyPlaces(CompleteTourInfo completeTourInfo) {
        if (completeTourInfo.getPlaces() != null) {
            Iterator<Place> placeIterator = completeTourInfo.getPlaces().iterator();
            while (placeIterator.hasNext()) {
                Place place = placeIterator.next();
                if (place.getCountry() == null && place.getPrice() == null
                        && place.getName() == null && place.getGeoWidth() == null
                        && place.getGeoHeight() == null) {
                    placeIterator.remove();
                }
            }
        }
    }

    private void checkTourInfo(CompleteTourInfo completeTourInfo) {
        for(TourInfo tourInfo: completeTourInfo.getTourInfo()) {
            if (tourInfo.getDiscount() == null) {
                tourInfo.setDiscount(0);
            }
        }
    }

    private void cleanTourUnsafeHTML(Tour tour) {
        tour.setName(Jsoup.clean(tour.getName(), Whitelist.basic()));
        tour.setDescription(Jsoup.clean(tour.getDescription(),
                Whitelist.basic()));
        tour.setTransportDesc(Jsoup.clean(tour.getTransportDesc(),
                Whitelist.basic()));
    }

    private void cleanPlacesUnsafeHTML(List<Place> places) {
        if (places != null) {
            for(Place place: places) {
                place.setName(Jsoup.clean(place.getName(), Whitelist.basic()));
                place.setDescription(Jsoup.clean(place.getDescription(), Whitelist.basic()));
            }
        }
    }
}
