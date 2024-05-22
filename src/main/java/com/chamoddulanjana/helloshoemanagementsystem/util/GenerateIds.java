package com.chamoddulanjana.helloshoemanagementsystem.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class GenerateIds {
    private static final Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(GenerateIds.class);

    public static String getId(String preFix) {
        StringBuilder stringBuilder = new StringBuilder(preFix);
        String timestamp = String.valueOf(System.currentTimeMillis());
        stringBuilder.append(timestamp.substring(timestamp.length() - 5)); // Append last 5 digits of current timestamp

        for (int i = 0; i < 3; i++) {
            stringBuilder.append(random.nextInt(10)); // Append a random digit (0-9)
        }
        String id = stringBuilder.toString();
        logger.info("Generated ID: {}", stringBuilder);
        return id;
    }
}
