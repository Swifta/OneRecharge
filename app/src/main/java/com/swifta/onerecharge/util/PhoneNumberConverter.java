package com.swifta.onerecharge.util;

/**
 * Created by moyinoluwa on 9/22/16.
 */

public class PhoneNumberConverter {

    public static String convert(String phoneNumber) {
        if (phoneNumber.length() == 14 && phoneNumber.substring(0, 4).equals("+234")) {
            phoneNumber = phoneNumber.replaceFirst("[+][2][3][4]", "0");
        }

        return phoneNumber;
    }
}
