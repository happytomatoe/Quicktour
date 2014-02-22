package com.quicktour.service;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.photos.upload.Ticket;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.quicktour.entity.*;
import com.quicktour.repository.PhotoRepository;
import org.scribe.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;


/**
 * Contains all functional logic connected with the images that are used in the system
 */
@Service
@DependsOn(value = {"myUserService", "companyService", "toursService"})
public class PhotoService {
    @Value("${defaultUserPhotoId}")
    private int DEFAULT_USER_PHOTO_ID;
    @Value("${defaultCompanyPhotoId}")
    private int DEFAULT_COMPANY_PHOTO_ID;
    private final Logger logger = LoggerFactory.getLogger(PhotoService.class);
    @Value("${flickRetryCount}")
    private int retryCount;
    private int tryCount;
    @Autowired
    private UsersService usersService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ToursService toursService;
    @Autowired
    private PhotoRepository photoRepository;

    @Value("${flickrApiKey}")
    private String apiKey;
    @Value("${flickrSecretKey}")
    private String secret;
    @Value("${accessToken}")
    private String accessToken;
    @Value("${accessTokenSecret}")
    private String accessTokenSecret;
    private static Flickr flickr;
    private static final Set<String> tickets = Collections.synchronizedSet(new LinkedHashSet());
    private static final List<PhotoHolder> waitingRecipients = Collections.synchronizedList(new ArrayList());


    @Scheduled(cron = "0/15 * * * * *")
    public void checkTickets() {
        if (tickets.size() > 0) {
            logger.debug("Tickets {}.WaitingRecipients {}", tickets, waitingRecipients);
            List<Ticket> checkedTickets = null;
            try {
                checkedTickets = flickr.getUploadInterface().checkTickets(tickets);
            } catch (FlickrException e) {
                logger.error("Exception while checking tickets.{}", e);
            }
            if (checkedTickets != null && checkedTickets.size() > 0) {
                int i = 0;
                Photo photo;
                logger.debug("Tickets {}. size {}", checkedTickets, checkedTickets.size());
                for (Ticket ticket : checkedTickets) {
                    if (ticket.hasCompleted()) {
                        Object waitingRecipient = waitingRecipients.get(i);
                        Photo newPhoto = createPhoto(ticket.getPhotoId());
                        if (waitingRecipient instanceof Tour) {
                            Tour tour = (Tour) waitingRecipient;
                            tour = toursService.findTourById(tour.getTourId());
                            photo = tour.getPhoto();
                            if (photo != null) {
                                photoRepository.delete(photo);
                            }
                            tour.setPhoto(newPhoto);
                            toursService.saveTour(tour);
                        } else if (waitingRecipient instanceof User) {
                            User user = (User) waitingRecipient;
                            logger.debug("Founded userId {}", user.getUserId());
                            int j = 0;
                            do {
                                user = usersService.findByUsername(user.getUsername());
                                j++;
                                if (user == null) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        logger.error("Cannot sleep anymore!.{}", e);
                                    }
                                }
                            } while (j < 5);
                            logger.debug("Founded user {}.Recipient {}", user, waitingRecipient);
                            photo = user.getPhoto();
                            if (photo != null) {
                                photoRepository.delete(photo);
                            }
                            user.setPhoto(newPhoto);
                            usersService.save(user);
                        } else if (waitingRecipient instanceof Company) {
                            Company company = (Company) waitingRecipient;
                            company = companyService.findOne(company.getCompanyId());
                            photo = company.getPhoto();
                            if (photo != null) {
                                photoRepository.delete(photo);
                            }
                            company.setPhoto(newPhoto);
                            companyService.saveAndFlush(company);
                        }
                        logger.debug("Removing ticket {}.Size {}", ticket, checkedTickets.size());
                        tickets.remove(ticket.getTicketId());
                        logger.debug("Size {}", checkedTickets.size());
                        waitingRecipients.remove(waitingRecipient);

                    }
                    i++;
                }
            }
        }

    }

    /**
     * Creates {@link com.quicktour.entity.Photo} from flickr photoId
     */
    private Photo createPhoto(String photoId) {
        try {
            authorize();
        } catch (FlickrException e) {
            logger.error("Cant authorize on flickr.{}", e.getMessage());
        }
        logger.debug("Create photo with photoId {}", photoId);
        com.flickr4java.flickr.photos.Photo info = null;
        Photo photo = new Photo();
        photo.setFlickrPhotoId(photoId);
        try {
            info = flickr.getPhotosInterface().getInfo(photoId, null);
        } catch (FlickrException e) {
            if (tryCount <= retryCount) {
                tryCount++;
                createPhoto(photoId);
            } else {
                logger.error("Cannot get info after 3 attempts on flick photo with photoId:{}", photoId);
            }
        } finally {
            tryCount = 0;
        }
        photo.setUrl(info.getMediumUrl());
        return photoRepository.saveAndFlush(photo);
    }

    @PostConstruct
    private void init() {
        if (flickr == null) {
            flickr = new Flickr(apiKey, secret, new REST());
            try {
                authorize();
            } catch (FlickrException e) {
                logger.error("Flickr exception while authorizing to Flickr. {}", e);
            }
        }
    }

    /**
     * Replaces existingPhto with newPhoto on flickr asynchronously
     *
     * @return ticketId from flickr
     */
    private String replaceImage(Photo existingPhoto, MultipartFile newPhoto) {
        try {
            authorize();
        } catch (FlickrException e) {
            logger.error("Cant authorize on flickr.{}", e.getMessage());
        }
        String ticketId = null;
        try {
            ticketId = flickr.getUploader().replace(newPhoto.getBytes(),
                    existingPhoto.getFlickrPhotoId(), true);
        } catch (FlickrException e) {
            logger.error("Cannot replace image on flickr .{}", e);
        } catch (IOException e) {
            logger.error("Cannot call getBytes on new image when replacing image.{}", e);
        }
        return ticketId;
    }

    /**
     * Uploads image to flickr asynchronously and returns ticketId
     */
    private String uploadImage(MultipartFile image) {
        if (image.isEmpty()) {
            return null;
        }
        try {
            authorize();
        } catch (FlickrException e) {
            logger.error("Cant authorize on flickr.{}", e.getMessage());
        }
        String ticketId = null;
        UploadMetaData metaData = new UploadMetaData();
        metaData.setContentType(Flickr.CONTENTTYPE_PHOTO);
        metaData.setHidden(true);
        metaData.setAsync(true);
        try {
            ticketId = flickr.getUploader().upload(image.getBytes(), metaData);
        } catch (FlickrException e) {
            logger.error("Flickr exception while uploading image to flickr.{}", e);
        } catch (IOException e) {
            logger.error("{}", e);
        }
        logger.info("Saved image.Response from TicketId:{}", ticketId);
        return ticketId;
    }

    private void authorize() throws FlickrException {
        if (RequestContext.getRequestContext().getAuth() != null) {
            return;
        }
        logger.debug("Trying to auhorize on flickr");
        try {
            Token accessToken2 = new Token(accessToken, accessTokenSecret);
            Auth auth = flickr.getAuthInterface().checkToken(accessToken2);
            logger.debug("Permission after auth: {}", auth.getPermission().toString());
            flickr.setAuth(auth);
            RequestContext.getRequestContext().setAuth(auth);

        } catch (FlickrException e) {
            if (tryCount > retryCount) {
                throw e;
            }
            tryCount++;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
                logger.error("{}", e);
            }
            authorize();
        } finally {
            tryCount = 0;
        }

    }


    private void uploadImageAndSaveTicket(MultipartFile image, Photo photo) {
        String ticketId;
        if (photo != null && photo.getFlickrPhotoId() != null && !photo.getFlickrPhotoId().isEmpty()) {
            ticketId = replaceImage(photo, image);
        } else {
            ticketId = uploadImage(image);
        }
        tickets.add(ticketId);
    }

    /**
     * Saves or replaces(if it is already exists) image on flickr and injects it into tour.
     * Also deletes existing photo
     */
    @Async
    public void saveImageAndSet(Tour tour, MultipartFile image) {
        logger.debug("saving and setting image to {}", tour);
        Photo photo = tour.getPhoto();
        uploadImageAndSaveTicket(image, photo);
        waitingRecipients.add(tour);
    }

    /**
     * Saves or replaces(if it is already exists) image on flickr and injects it into user.
     * Also deletes existing photo
     */
    @Async
    public void saveImageAndSet(User user, MultipartFile image) {
        logger.debug("saving and setting image to {}", user);
        uploadImageAndSaveTicket(image, user.getPhoto());
        waitingRecipients.add(user);
    }

    /**
     * Saves or replaces(if it is already exists) image on flickr and injects it into company.
     * Also deletes existing photo
     */
    @Async
    public void saveImageAndSet(Company company, MultipartFile image) {
        logger.debug("saving and setting image to {}", company);
        uploadImageAndSaveTicket(image, company.getPhoto());
        waitingRecipients.add(company);
    }


}
