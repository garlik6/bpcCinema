package com.example.oauthlogin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
@Service
public class PictureSaverService {

    @Value("${profilePicture.path}")
    public void setProfilePicturePath(String profilePicturePath) {
        PictureSaverService.profilePicturePath = profilePicturePath;
    }

    @Value("${profilePicture.format}")
    public void setProfilePictureFormat(String profilePictureFormat) {
        ProfilePictureFormat = profilePictureFormat;
    }


    private static String profilePicturePath;

    private static String ProfilePictureFormat;

    public static String savePicture(String profilePicURL, String username) throws IOException {
        assert profilePicURL != null;
        BufferedImage img = ImageIO.read(new URL(profilePicURL));
        String pathToPic = profilePicturePath + username + "_pic." + ProfilePictureFormat;
        File outputFile = new File(pathToPic);
        ImageIO.write(img, ProfilePictureFormat, outputFile);
        return pathToPic;
    }
}
