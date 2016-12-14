package com.swifta.onerecharge.agentdashboard;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swifta.onerecharge.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileRulesFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private static final String defaultValue = "0.00";

    @BindView(R.id.msisdn_purchase_limit_text)
    TextView msisdnPurchaseLimitText;
    @BindView(R.id.minimum_wallet_balance_text)
    TextView minimumWalletBalanceText;
    @BindView(R.id.airtime_purchase_discount_text)
    TextView airtimePurchaseDiscountText;
    @BindView(R.id.airtime_sold_commission_text)
    TextView airtimeSoldCommissionText;


    public ProfileRulesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_rules, container,
                false);

        ButterKnife.bind(this, view);

        sharedPreferences = getActivity().getSharedPreferences(getString(R
                .string.agent_shared_preference_name), Context.MODE_PRIVATE);

        setUpUiWithSavedValues();

        return view;
    }

    private String getMSISDNPurchaseLimit() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_msisdn_purchase_limit), defaultValue);
    }

    private String getMinimumWalletBalance() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_minimum_wallet_balance), defaultValue);
    }

    private String getAirtimePurchaseDiscount() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_airtime_purchase_discount), defaultValue);
    }

    private String getAirtimeSoldCommission() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_airtime_sold_commission), defaultValue);
    }

    private void setUpUiWithSavedValues() {
        msisdnPurchaseLimitText.setText(getResources().getString(R.string
                .msisdn_purchase_limit_value, getMSISDNPurchaseLimit()));

        minimumWalletBalanceText.setText(getResources().getString(R.string
                .minimum_wallet_ballance_value, getMinimumWalletBalance()));

        airtimePurchaseDiscountText.setText(getResources().getString(R.string
                .airtime_purchase_discount_value, getAirtimePurchaseDiscount()));

        airtimeSoldCommissionText.setText(getResources().getString(R.string
                .airtime_sold_commission_value, getAirtimeSoldCommission()));
    }
}
