package com.quicktour.controller;


import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Controller that resolves links for images used in the system
 *
 * @author Andrew Zarichnyi
 * @version 1.0 12/27/2013
 */
@Controller
public class PhotoController {
    @Value("${imageDirectory}")
    private String imageDirectory;

    /**
     * Reads image file from the local filesystem and maps its bytes to http response that
     * will be send back to the ui.
     *
     * @param response - http response that will contain image file and will be sent to the ui
     * @param photoUrl - filename of the image requested
     * @throws IOException - in case of file read\write errors
     */
    @RequestMapping(value = "/images/{photoUrl}", method = RequestMethod.GET)
    public void getUserImage(HttpServletResponse response, @PathVariable("photoUrl") String photoUrl)
            throws IOException {
        response.setContentType("image/jpeg");
        InputStream in1 = new FileInputStream(imageDirectory + photoUrl + ".jpg");
        IOUtils.copy(in1, response.getOutputStream());
    }
}
