package com.swifta.onerecharge.networklist;

import java.util.List;

/**
 * Created by moyinoluwa on 9/28/16.
 */

public class NetworkListRepository {

    private static List<String> networkList;

    public static List<String> getNetworkList() {
        return networkList;
    }

    public static void setNetworkList(List<String> newNetworkList) {
        networkList = newNetworkList;
    }
}
