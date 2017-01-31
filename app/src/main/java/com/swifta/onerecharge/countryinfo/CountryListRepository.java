package com.swifta.onerecharge.countryinfo;

import java.util.List;

/**
 * Created by moyinoluwa on 1/30/17.
 */

public class CountryListRepository {
    private static List<String> countryList;

    public static List<String> getCountryList() {
        return countryList;
    }

    public static void setCountryList(List<String> newCountryList) {
        countryList = newCountryList;
    }
}
