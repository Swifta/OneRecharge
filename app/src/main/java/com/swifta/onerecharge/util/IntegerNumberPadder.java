package com.swifta.onerecharge.util;

/**
 * Created by moyinoluwa on 9/20/16.
 */
public class IntegerNumberPadder {

    public static String addOneZeroPrefix(int number) {
        String paddedNumber;

        if (number < 10) {
            paddedNumber = "0" + number;
        } else {
            paddedNumber = String.valueOf(number);
        }

        return paddedNumber;
    }
}
