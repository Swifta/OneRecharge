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

    @BindView(R.id.country_spinner)
    Spinner countryChoiceSpinner;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.amount_layout)
    TextInputLayout amountFieldLayout;

    @BindView(R.id.topUpView)
    LinearLayout topUpView;

    String amount;

    private SharedPreferences sharedPreferences;

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

        sharedPreferences = getActivity().getSharedPreferences(getString(R.string
                .agent_shared_preference_name), Context.MODE_PRIVATE);

        if (isOtpTransaction()) {
            switchToOtpView();
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agent_wallet_top_up, container, false);

        ButterKnife.bind(this, view);

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

    @Override
    public void onResume() {
        super.onResume();

        if (isOtpTransaction()) {
            switchToOtpView();
        }
    }

    private boolean isOtpTransaction() {
        return sharedPreferences.getBoolean(getResources().getString(R.string
                .saved_agent_wallet_otp_status), false);
    }

    private void switchToOtpView() {
        Intent paymentActivityIntent = new Intent(getActivity(),
                AgentWalletTopUpPaymentActivity.class);
        startActivity(paymentActivityIntent);
    }

    @OnClick(R.id.top_wallet_up_button)
    void fundWallet() {

        amountFieldLayout.setError(null);

        amount = amountField.getText().toString();

        boolean cancel = false;
        View focusView = null;

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
        paymentActivityIntent.putExtra("agent_token", getToken());
        paymentActivityIntent.putExtra("email", getEmailAddress());
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
