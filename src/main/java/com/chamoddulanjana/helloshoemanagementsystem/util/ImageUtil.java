package com.chamoddulanjana.helloshoemanagementsystem.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Component
public class ImageUtil {
    public static String convertBase64(MultipartFile imageFile) throws IOException {
        return Base64.getEncoder().encodeToString(imageFile.getBytes());
    }
}
