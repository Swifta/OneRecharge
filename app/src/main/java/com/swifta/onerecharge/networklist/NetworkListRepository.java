package com.swifta.onerecharge.networklist;

import java.util.List;

/**
 * Created by moyinoluwa on 9/28/16.
 */

public class NetworkListRepository {

    private static List<List<String>> countryNetworkList;

    public static List<List<String>> getNetworkList() {
        return countryNetworkList;
    }

    public static void setNetworkList(List<List<String>> newNetworkList) {
        countryNetworkList = newNetworkList;
    }
}
