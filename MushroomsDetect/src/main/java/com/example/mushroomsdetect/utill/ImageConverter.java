package com.example.mushroomsdetect.utill;

import java.util.Base64;

public class ImageConverter {

    public static String convertBytesToBase64(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return Base64.getEncoder().encodeToString(bytes);
    }
}
