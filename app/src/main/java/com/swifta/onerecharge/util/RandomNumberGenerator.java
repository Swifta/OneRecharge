package com.swifta.onerecharge.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by moyinoluwa on 2/7/17.
 */
public class RandomNumberGenerator {

    public static final char[] CHARSET_AZ_09 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    public static String getRandomString(int length) {
        Random random = new SecureRandom();
        char[] result = new char[length];
        for (int i = 0; i < result.length; i++) {
            // picks a random index out of character set > random character
            int randomCharIndex = random.nextInt(CHARSET_AZ_09.length);
            result[i] = CHARSET_AZ_09[randomCharIndex];
        }
        return new String(result);
    }
}
