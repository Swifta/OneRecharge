package com.swifta.onerecharge.agent.agentquicktransactionhistory;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by moyinoluwa on 9/26/16.
 */

public class QuickRechargeHistoryCallback extends DiffUtil.Callback {

    private final List<Row> oldList;
    private final List<Row> newList;

    public QuickRechargeHistoryCallback(List<Row> oldList, List<Row> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Row oldItem = oldList.get(oldItemPosition);
        final Row newItem = newList.get(newItemPosition);

        return oldItem.getAmount().equals(newItem.getAmount());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
