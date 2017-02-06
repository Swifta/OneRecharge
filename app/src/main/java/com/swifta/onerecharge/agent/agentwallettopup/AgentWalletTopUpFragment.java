package com.swifta.onerecharge.agent.agentwallettopup;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.swifta.onerecharge.R;
import com.swifta.onerecharge.agent.agentquickrecharge.RechargeResponseFragment;
import com.swifta.onerecharge.agent.agentwallettopup.agentwallettopuppayment.AgentWalletTopUpPaymentActivity;
import com.swifta.onerecharge.countryinfo.CountryListRepository;
import com.swifta.onerecharge.util.InternetConnectivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgentWalletTopUpFragment extends Fragment {

    @BindView(R.id.amount)
    TextInputEditText amountField;
    @BindView(R.id.reference)
    TextInputEditText referenceField;
    @BindView(R.id.agent_phone_text)
    TextInputEditText phoneNumberField;

    @BindView(R.id.country_spinner)
    Spinner countryChoiceSpinner;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.amount_layout)
    TextInputLayout amountFieldLayout;
    @BindView(R.id.reference_layout)
    TextInputLayout referenceLayout;
    @BindView(R.id.agent_phone_layout)
    TextInputLayout phoneLayout;

    @BindView(R.id.topUpView)
    LinearLayout topUpView;

    String amount;
    String reference;
    String phoneNumber;

    private SharedPreferences sharedPreferences;
    RechargeResponseFragment successfulFragment;

    private static final int TRANSACTION_FAILED = 0;
    private static final int TRANSACTION_SUCCESSFUL = 1;

    public AgentWalletTopUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayAdapter<String> adapter;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agent_wallet_top_up, container, false);

        ButterKnife.bind(this, view);
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string
                .agent_shared_preference_name), Context.MODE_PRIVATE);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle
                (getResources().getString(R.string.wallet_topup));

        if (CountryListRepository.getCountryList() != null) {
            List<String> countryList = CountryListRepository.getCountryList();
            String[] countries = countryList.toArray(new String[countryList.size()]);

            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
                    countries);
        } else {
            String[] countries = {"Nigeria", "Ghana"};
            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout
                    .simple_spinner_item, countries);
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countryChoiceSpinner.setAdapter(adapter);

        return view;
    }

    @OnClick(R.id.top_wallet_up_button)
    void fundWallet() {

        amountFieldLayout.setError(null);
        referenceLayout.setError(null);
        phoneLayout.setError(null);

        amount = amountField.getText().toString();
        reference = referenceField.getText().toString();
        phoneNumber = phoneNumberField.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (phoneNumber.isEmpty()) {
            phoneLayout.setError(getString(R.string.phone_number_empty_error));
            focusView = phoneLayout;
            cancel = true;
        } else {
            phoneLayout.setError(null);
        }

        if (reference.isEmpty()) {
            referenceLayout.setError(getString(R.string.reference_empty_error));
            focusView = referenceLayout;
            cancel = true;
        } else {
            referenceLayout.setError(null);
        }

        if (amount.isEmpty()) {
            amountFieldLayout.setError(getString(R.string.amount_empty_error));
            focusView = amountFieldLayout;
            cancel = true;
        } else {
            amountFieldLayout.setError(null);
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (!InternetConnectivity.isDeviceConnected(getActivity())) {
                Snackbar.make(topUpView, R.string.internet_error, Snackbar.LENGTH_SHORT).show();
            } else {
                switchToPaymentActivity();
            }
        }
    }

    private void switchToPaymentActivity() {
        Intent paymentActivityIntent = new Intent(getActivity(),
                AgentWalletTopUpPaymentActivity.class);

        paymentActivityIntent.putExtra("amount", Integer.valueOf(amount));
        paymentActivityIntent.putExtra("reference_id", reference);
        paymentActivityIntent.putExtra("agent_token", getToken());
        paymentActivityIntent.putExtra("email", getEmailAddress());
        paymentActivityIntent.putExtra("agent_telephone", phoneNumber);
        paymentActivityIntent.putExtra("country", countryChoiceSpinner.getSelectedItem().toString
                ());
        startActivity(paymentActivityIntent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    private String getEmailAddress() {
        return sharedPreferences.getString(getResources().getString(R.string.saved_email_address)
                , "");
    }

    private String getToken() {
        return sharedPreferences.getString(getResources().getString(R.string.saved_auth_token), "");
    }
}
