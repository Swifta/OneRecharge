package com.swifta.onerecharge.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by moyinoluwa on 8/16/16.
 */
public class EmailRegexValidator {

    public static boolean isValidEmail(final String email) {
        String EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";


        Pattern pattern = Pattern.compile(EMAIL_PATTERN,
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
