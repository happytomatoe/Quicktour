package com.quicktour.service;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.flickr4java.flickr.uploader.Uploader;
import com.quicktour.entity.Company;
import com.quicktour.entity.Photo;
import com.quicktour.entity.User;
import com.quicktour.repository.PhotoRepository;
import org.scribe.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import java.io.IOException;


/**
 * Contains all functional logic connected with the images that are used in the system
 *
 * @author Andrew Zarichnyi
 * @version 1.0 12/27/2013
 */
@Service
@Transactional
public class PhotoService {

    private static final int DEFAULT_AVATAR_ID = 4;
    private static final int DEFAULT_LOGO_ID = 5;
    private static final String FLICKR_COM = "flickr.com";
    public final Logger logger = LoggerFactory.getLogger(PhotoService.class);
    @Autowired
    private UsersService usersService;
    @Autowired
    CompanyService companyService;
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

    @PostConstruct
    private void init() {
        if (flickr == null) {
            flickr = new Flickr(apiKey, secret, new REST());
            try {
                authorize();
            } catch (FlickrException e) {
                logger.error("Flickr exception while authorizing to Flickr. {}", e);
            } catch (IOException e) {
                logger.error("IOException while authorizing to Flickr.{}", e);
            } catch (SAXException e) {
                logger.error("SAXException while authorizing to Flickr.{}", e);
            }
        }
    }


    /**
     * Saves image that user uploads in registration forms to the filesystem
     *
     * @param filename - filename of the file to be saved
     * @param image    - contains file that user uploads
     * @return - returns true if all is OK and false if we have errors
     */
    public Photo saveImage(String filename, MultipartFile image) {
        if (image.isEmpty()) {
            return null;
        }
        String photoId = null;
        User currentUser = usersService.getCurrentUser();
        Uploader uploader = flickr.getUploader();
        UploadMetaData metaData = new UploadMetaData();
        metaData.setContentType(Flickr.CONTENTTYPE_PHOTO);
        metaData.setHidden(false);
        String title = "";
        if (filename != null) {
            title = filename;
        } else {
            Company company = null;
            if (currentUser != null) {
                company = companyService.getCompanyByUserId(currentUser.getUserId());
            }
            if (company != null) {
                title += company.toString() + ":";
            }
            title += currentUser.getLogin();
        }
        metaData.setTitle(title);
        com.flickr4java.flickr.photos.Photo info = null;
        try {
            authorize();
            photoId = uploader.upload(image.getBytes(), metaData);
            info = flickr.getPhotosInterface().getInfo(photoId, null);
        } catch (FlickrException e) {
            logger.error("Flickr exception while saving image. {}", e);
            return null;
        } catch (IOException e) {
            logger.error("IOException while saving image .{}", e);
            return null;
        } catch (SAXException e) {
            logger.error("IOException while authorize .{}", e);
            return null;
        }
        logger.info("Saved image.Response from Flickr:{}", photoId);
        Photo photo = new Photo();
        photo.setUrl(info.getMediumUrl());
        photo = photoRepository.saveAndFlush(photo);
        if (photo.getPhotoId() == 0) {
            throw new NullPointerException("Cant save image " + photo.getPhotoId() + " " + photo.getUrl());
        }
        logger.debug("Saved image: {},{}", photo.getPhotoId(), photo.getUrl());

        return photo;
    }

    private void authorize() throws IOException, SAXException, FlickrException {
        if (RequestContext.getRequestContext().getAuth() != null) {
            return;
        }
        logger.debug("Trying to auhorize on flickr");
        Token accessToken2 = new Token(accessToken, accessTokenSecret);
        Auth auth = flickr.getAuthInterface().checkToken(accessToken2);
        flickr.setAuth(auth);
        RequestContext.getRequestContext().setAuth(auth);
    }


    public Photo getDefaultAvatar() {
        return photoRepository.findOne(DEFAULT_AVATAR_ID);
    }

    public boolean deletePhoto(Photo photo) {
        //TODO:test
        if (photo == null) {
            return false;
        }
        String url = photo.getUrl();
        if (url.contains(FLICKR_COM)) {
            String photoId = url.substring(url.lastIndexOf("/"));
            try {
                flickr.getPhotosInterface().delete(photoId);
            } catch (FlickrException e) {
                logger.error("Cannot delete photo on flick with photoId {}.{}", photoId, e);
            }
            photoRepository.delete(photo.getPhotoId());
        }
        return true;
    }


}
