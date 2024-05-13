package com.chamoddulanjana.helloshoemanagementsystem.util;

import java.util.Base64;

public class ImageUtil {
    public static String convertBase64(String data){
        return Base64.getEncoder().encodeToString(data.getBytes());
    }
}
