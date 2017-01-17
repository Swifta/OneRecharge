package com.swifta.onerecharge.agentdashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.swifta.onerecharge.R;
import com.swifta.onerecharge.util.InstantTimeFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileSummaryFragment extends Fragment {
    private SharedPreferences sharedPreferences;

    @BindView(R.id.referral_id)
    TextView referralIdTextView;
    @BindView(R.id.agent_type)
    TextView agentType;
    @BindView(R.id.agent_class)
    TextView agentClass;
    @BindView(R.id.date_and_time_created)
    TextView dateAndTimeCreated;
    @BindView(R.id.total_transactions)
    TextView totalTransactions;
    @BindView(R.id.total_successful_transactions)
    TextView totalSuccessfulTransactionsTextView;
    @BindView(R.id.total_amount_sold)
    TextView totalAmountSoldTextView;
    @BindView(R.id.last_successful_amount)
    TextView lastSuccessfulAmountTextview;
    @BindView(R.id.last_successful_time)
    TextView lastSuccessfulTimeTextview;
    @BindView(R.id.total_failed_transactions)
    TextView totalFailedTransactionsTextView;
    @BindView(R.id.total_failed_transaction_amount)
    TextView totalFailedTransactionsAmountTextView;
    @BindView(R.id.last_failed_transaction_amount)
    TextView lastFailedAmountTextView;
    @BindView(R.id.last_failed_transaction_time)
    TextView lastFailedTimeTextview;
    @BindView(R.id.failed_transaction_description)
    TextView failedDescriptionTextview;

    @BindView(R.id.referral_layout)
    RelativeLayout referralLayout;
    @BindView(R.id.horizontal_line)
    View horizontalLine;


    public ProfileSummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_summary, container, false);

        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        sharedPreferences = getActivity().getSharedPreferences(getString(R
                .string.agent_shared_preference_name), Context.MODE_PRIVATE);

        setUpUiWithSavedValues();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.agent, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.sort_by_date);
        setMenuItemToFalse(item);

        item = menu.findItem(R.id.sort_by_recipient);
        setMenuItemToFalse(item);

        item = menu.findItem(R.id.sort_by_amount);
        setMenuItemToFalse(item);

        item = menu.findItem(R.id.sort_by_network);
        setMenuItemToFalse(item);

        item = menu.findItem(R.id.sort_by_status);
        setMenuItemToFalse(item);

        item = menu.findItem(R.id.action_settings);
        item.setVisible(true);

        item = menu.findItem(R.id.action_logout);
        item.setVisible(true);
    }

    private void setMenuItemToFalse(MenuItem item) {
        if (item != null) {
            item.setVisible(false);
        }
    }

    private String getReferralId() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_referral_id), "");
    }

    private String getAgentType() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_agent_type), "");
    }

    private String getAgentClass() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_agent_class), "");
    }

    private String getDateAndTimeCreated() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_date_and_time), "");
    }

    private int getTotalTransactions() {
        return sharedPreferences.getInt(getResources().getString(R.string
                .saved_total_transactions), 0);
    }

    private int getSuccessfulTransactions() {
        return sharedPreferences.getInt(getResources().getString(R.string
                .saved_successful_transactions), 0);
    }

    private int getTotalAmountSold() {
        return sharedPreferences.getInt(getResources().getString(R.string
                .saved_total_amount_sold), 0);
    }

    private int getLastSuccessfulAmount() {
        return sharedPreferences.getInt(getResources().getString(R.string
                .saved_last_successful_amount), 0);
    }

    private String getLastSuccessfulTime() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_last_successful_time), "");
    }

    private int getTotalFailedTransactions() {
        return sharedPreferences.getInt(getResources().getString(R.string
                .saved_total_failed_transaction), 0);
    }

    private int getTotalFailedTransactionAmount() {
        return sharedPreferences.getInt(getResources().getString(R.string
                .saved_total_failed_transaction_amount), 0);
    }

    private int getLastFailedAmount() {
        return sharedPreferences.getInt(getResources().getString(R.string
                .saved_last_failed_amount), 0);
    }

    private String getLastFailedTime() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_last_failed_time), "");
    }

    private String getFailedTransactionDesc() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_failed_transaction_description), "");
    }

    private void setUpUiWithSavedValues() {
        String time = "";

        if (!getReferralId().equals("")) {
            referralLayout.setVisibility(View.VISIBLE);
            horizontalLine.setVisibility(View.VISIBLE);
            referralIdTextView.setText(getResources().getString(R.string.your_referral_id_value,
                    getReferralId()));
        }

        agentType.setText(getResources().getString(R.string.agent_type_value, getAgentType()));
        agentClass.setText(getResources().getString(R.string.agent_class_value, getAgentClass()));
        if(getDateAndTimeCreated().length() > 0) {
            time = InstantTimeFormatter.formatInstantTime(getDateAndTimeCreated());
        }
        dateAndTimeCreated.setText(getResources().getString(R.string
                .date_and_time_created_value, time));
        totalTransactions.setText(getResources().getString(R.string
                .total_transactions_value, getTotalTransactions()));
        totalSuccessfulTransactionsTextView.setText(getResources().getString(R.string
                .total_successful_transactions_value, getSuccessfulTransactions()));
        totalAmountSoldTextView.setText(getResources().getString(R.string
                .total_amount_sold_value, getTotalAmountSold()));
        lastSuccessfulAmountTextview.setText(getResources().getString(R.string
                .last_successful_transaction_amount_value, getLastSuccessfulAmount()));
        if(getLastSuccessfulTime().length() > 0) {
            time = InstantTimeFormatter.formatInstantTime(getLastSuccessfulTime());
        }
        lastSuccessfulTimeTextview.setText(getResources().getString(R.string
                .last_successful_transaction_time_value, time));
        totalFailedTransactionsTextView.setText(getResources().getString(R.string
                .total_failed_transactions_value, getTotalFailedTransactions()));
        totalFailedTransactionsAmountTextView.setText(getResources().getString(R.string
                .total_failed_transaction_amount_value, getTotalFailedTransactionAmount()));
        lastFailedAmountTextView.setText(getResources().getString(R.string
                .last_failed_transaction_amount_value, getLastFailedAmount()));
        if(getLastFailedTime().length() > 0) {
            time = InstantTimeFormatter.formatInstantTime(getLastFailedTime());
        }
        lastFailedTimeTextview.setText(getResources().getString(R.string
                .last_failed_transaction_time_value, time));
        failedDescriptionTextview.setText(getResources().getString(R.string
                .failed_transaction_description_value, getFailedTransactionDesc()));
    }
}
