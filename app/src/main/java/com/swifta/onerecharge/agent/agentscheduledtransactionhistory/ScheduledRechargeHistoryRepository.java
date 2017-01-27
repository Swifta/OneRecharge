package com.swifta.onerecharge.agent.agentscheduledtransactionhistory;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by moyinoluwa on 9/26/16.
 */

public class ScheduledRechargeHistoryRepository {
    public static List<RealmRow> rowList = new ArrayList<>();

    public ScheduledRechargeHistoryRepository(List<RealmRow> rows) {
        rowList = rows;
    }

    public static void setOriginalHistoryList(List<RealmRow> rowLists) {
        rowList = rowLists;
    }

    public static List<RealmRow> getOriginalHistoryList() {
        return rowList;
    }

    public static List<RealmRow> getHistoryListSortedByRecipient() {
        final List<RealmRow> rowList = getOriginalHistoryList();

        Collections.sort(rowList, (o1, o2) -> o1.getRecipient().compareTo(o2.getRecipient()));

        return rowList;
    }

    public static List<RealmRow> getHistoryListSortedByDate() {
        final List<RealmRow> rowList = getOriginalHistoryList();

        Collections.sort(rowList, (o1, o2) -> o2.getCreatedTime().compareTo(o1.getCreatedTime()));

        return rowList;
    }

    public static List<RealmRow> getHistoryListSortedByNetwork() {
        final List<RealmRow> rowList = getOriginalHistoryList();

        Collections.sort(rowList, (o1, o2) -> o1.getNetwork().compareTo(o2.getNetwork()));

        return rowList;
    }

    public static List<RealmRow> getHistoryListSortedByAmount() {
        final List<RealmRow> rowList = getOriginalHistoryList();

        Collections.sort(rowList, (o1, o2) -> o2.getAmount() - o1.getAmount());

        return rowList;
    }

    public static List<RealmRow> getHistoryListSortedByStatus() {
        final List<RealmRow> rowList = getOriginalHistoryList();

        Collections.sort(rowList, (o1, o2) -> o1.getSuccessful().toString().compareTo(o2
                .getSuccessful().toString()));

        return rowList;
    }

}
