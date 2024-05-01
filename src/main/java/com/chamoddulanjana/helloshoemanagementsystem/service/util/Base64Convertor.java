package com.chamoddulanjana.helloshoemanagementsystem.service.util;

import java.util.Base64;

public class Base64Convertor {
    public static String convertBase64(String data){
        return Base64.getEncoder().encodeToString(data.getBytes());
    }
}
