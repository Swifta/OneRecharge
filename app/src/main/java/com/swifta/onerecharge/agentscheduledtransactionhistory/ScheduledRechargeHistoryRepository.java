package com.swifta.onerecharge.agentscheduledtransactionhistory;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by moyinoluwa on 9/26/16.
 */

public class ScheduledRechargeHistoryRepository {
    public static List<Row> rowList = new ArrayList<>();

    public ScheduledRechargeHistoryRepository(List<Row> rows) {
        rowList = rows;
    }

    public static void setOriginalHistoryList(List<Row> rowLists) {
        rowList = rowLists;
    }

    public static List<Row> getOriginalHistoryList() {
        return rowList;
    }

    public static List<Row> getHistoryListSortedByRecipient() {
        final List<Row> rowList = getOriginalHistoryList();

        Collections.sort(rowList, (o1, o2) -> o1.getRecipient().compareTo(o2.getRecipient()));

        return rowList;
    }

    public static List<Row> getHistoryListSortedByDate() {
        final List<Row> rowList = getOriginalHistoryList();

        Collections.sort(rowList, (o1, o2) -> o2.getCreatedTime().compareTo(o1.getCreatedTime()));

        return rowList;
    }

    public static List<Row> getHistoryListSortedByNetwork() {
        final List<Row> rowList = getOriginalHistoryList();

        Collections.sort(rowList, (o1, o2) -> o1.getNetwork().compareTo(o2.getNetwork()));

        return rowList;
    }

    public static List<Row> getHistoryListSortedByAmount() {
        final List<Row> rowList = getOriginalHistoryList();

        Collections.sort(rowList, (o1, o2) -> o2.getAmount() - o1.getAmount());

        return rowList;
    }

    public static List<Row> getHistoryListSortedByStatus() {
        final List<Row> rowList = getOriginalHistoryList();

        Collections.sort(rowList, (o1, o2) -> o1.getSuccessful().toString().compareTo(o2.getSuccessful().toString()));

        return rowList;
    }

}
