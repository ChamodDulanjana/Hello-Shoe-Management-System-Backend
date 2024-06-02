package com.chamoddulanjana.helloshoesapplicationsystem.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Component
public class Base64Encoder {
    public String encodeImage(MultipartFile imageFile) throws IOException {
        return Base64.getEncoder().encodeToString(imageFile.getBytes());
    }
}
