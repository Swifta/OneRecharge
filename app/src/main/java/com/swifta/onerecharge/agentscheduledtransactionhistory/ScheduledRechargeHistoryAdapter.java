package com.swifta.onerecharge.agentscheduledtransactionhistory;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.swifta.onerecharge.R;
import com.swifta.onerecharge.util.InstantTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by moyinoluwa on 9/26/16.
 */

public class ScheduledRechargeHistoryAdapter extends RecyclerView
        .Adapter<ScheduledRechargeHistoryAdapter.ViewHolder> {

    private List<RealmRow> scheduledHistoryList = new ArrayList<>();
    private Context context;

    public ScheduledRechargeHistoryAdapter(List<RealmRow> scheduledHistoryList) {
        this.scheduledHistoryList.addAll(scheduledHistoryList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.scheduled_transaction_history_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RealmRow scheduledTransactionHistory = scheduledHistoryList.get(position);
        holder.recipientTextView.setText(scheduledTransactionHistory.getRecipient());

        String networkType = scheduledTransactionHistory.getNetwork();
        switch (networkType) {
            case "Airtel":
                holder.networkIconView.setBackgroundResource(R.drawable.airtel);
                break;
            case "Etisalat":
                holder.networkIconView.setBackgroundResource(R.drawable.etisalat);
                break;
            case "Glo":
                holder.networkIconView.setBackgroundResource(R.drawable.glo);
                break;
            case "MTN":
                holder.networkIconView.setBackgroundResource(R.drawable.mtn);
                break;
        }

        String time = InstantTimeFormatter.formatInstantTime(scheduledTransactionHistory.getCreatedTime());
        holder.rechargeTimeTextView.setText(time);

        boolean isSuccessful = scheduledTransactionHistory.getSuccessful();
        if (isSuccessful) {
            holder.statusTextView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
            holder.statusTextView.setText("Successful");
        } else {
            holder.statusTextView.setTextColor(ContextCompat.getColor(context, android.R.color
                    .holo_red_dark));
            holder.statusTextView.setText("Failed");
        }

        String amount = context.getResources().getString(R.string.total_amount_sold_value, scheduledTransactionHistory.getAmount());
        holder.amountTextView.setText(amount);
    }

    public void swapItems(List<RealmRow> history) {
        final ScheduledRechargeHistoryCallback diffCallback = new ScheduledRechargeHistoryCallback(this
                .scheduledHistoryList, history);

        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.scheduledHistoryList.clear();
        this.scheduledHistoryList.addAll(history);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return scheduledHistoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.network_icon)
        ImageView networkIconView;
        @BindView(R.id.recipient)
        TextView recipientTextView;
        @BindView(R.id.recharge_time)
        TextView rechargeTimeTextView;
        @BindView(R.id.status)
        TextView statusTextView;
        @BindView(R.id.amount)
        TextView amountTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
