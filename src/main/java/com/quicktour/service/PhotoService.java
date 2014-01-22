package com.quicktour.service;

import com.quicktour.entity.Photo;
import com.quicktour.repository.PhotoRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Contains all functional logic connected with the images that are used in the system
 * @author Andrew Zarichnyi
 * @version 1.0 12/27/2013
 */
@Service
public class PhotoService {

    private static int DEFAULT_AVATAR_ID = 4;
    private static int DEFAULT_LOGO_ID = 5;

    public Logger logger;
    @Autowired
    private PhotoRepository photoRepository;

    /**
     * Describes absolute url of directory where images are stored
     */
    @Value("${webRootPath}")
    private String webRootPath;


    /**
     * Saves image that user uploads in registration forms to the filesystem
     *
     * @param filename - filename of the file to be saved
     * @param image - contains file that user uploads
     * @return - returns true if all is OK and false if we have errors
     */
    public boolean saveImage(String filename, MultipartFile image){
        if (image.isEmpty()){
            return false;
        }
    try{
            File file = new File(webRootPath + filename);
            FileUtils.writeByteArrayToFile(file, image.getBytes());

    } catch (IOException io){
               //logger.log
                return false;
    }  catch (NullPointerException npe){
        //TODO set
        return false;
    }
    Photo photo = new Photo();
    photo.setPhotoUrl(filename);
    if(photoRepository.findByPhotoUrl(filename) == null)
        photoRepository.saveAndFlush(photo);
    return true;
}
    public Photo findByPhotoUrl(String photoUrl){
        return photoRepository.findByPhotoUrl(photoUrl);
    }

    public Photo saveAvatar(String filename, MultipartFile image){
        if(saveImage(filename, image))
            return photoRepository.findByPhotoUrl(filename);
        else return photoRepository.findOne(DEFAULT_AVATAR_ID);
    }

    public Photo saveLogo(String filename, MultipartFile image){
        if(saveImage(filename, image))
            return photoRepository.findByPhotoUrl(filename);
        else return photoRepository.findOne(DEFAULT_LOGO_ID);
    }
    public Photo findOne(Integer id){
        return photoRepository.findOne(id);
    }
}
