package com.swifta.onerecharge.customer.customerquickrecharge;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.swifta.onerecharge.R;
import com.swifta.onerecharge.agent.agentquickrecharge.RechargeResponseFragment;
import com.swifta.onerecharge.countryinfo.CountryListRepository;
import com.swifta.onerecharge.customer.CustomerActivity;
import com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargepayment.CustomerQuickRechargePaymentActivity;
import com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargerequestmodel.CustomerQuickRechargeRequest;
import com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargeresponsemodel.CustomerQuickRechargeResponse;
import com.swifta.onerecharge.networklist.NetworkListRepository;
import com.swifta.onerecharge.util.CustomerService;
import com.swifta.onerecharge.util.InternetConnectivity;
import com.swifta.onerecharge.util.PhoneNumberConverter;
import com.swifta.onerecharge.util.Url;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerQuickRechargeFragment extends Fragment {
    @BindView(R.id.quick_recharge_phone_text)
    TextInputEditText quickRechargePhoneText;
    @BindView(R.id.quick_recharge_phone_layout)
    TextInputLayout quickRechargePhoneLayout;
    @BindView(R.id.country_spinner)
    Spinner countryChoiceSpinner;
    @BindView(R.id.network_spinner)
    Spinner networkChoiceSpinner;
    @BindView(R.id.quick_recharge_amount)
    TextInputEditText quickRechargeAmountText;
    @BindView(R.id.quick_recharge_amount_layout)
    TextInputLayout quickRechargeAmountLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.quick_recharge_container)
    LinearLayout quickRechargeContainer;

    private SharedPreferences sharedPreferences;
    RechargeResponseFragment successfulFragment;

    String phoneNumber;
    Integer amount;
    String networkProvider;

    private static final int TRANSACTION_FAILED = 0;
    private static final int TRANSACTION_SUCCESSFUL = 1;
    private static final String TRANSACTION_SUCCESSFUL_MESSAGE = "Transaction request sent " +
            "successfully!";
    private static final String TRANSACTION_ERROR_MESSAGE = "Transaction " +
            "unsuccessful. Please try again.";

    private static final int CONTACT_PICKER_RESULT = 200;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 201;

    public CustomerQuickRechargeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_customer_quick_recharge, container,
                false);

        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle
                (getResources().getString(R.string.quick_recharge));

        sharedPreferences = getActivity().getSharedPreferences(getString(R
                .string.customer_shared_preference_name), Context.MODE_PRIVATE);

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
        countryChoiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    setNetworkAdapter(position);
                } else if (position == 1) {
                    setNetworkAdapter(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setNetworkAdapter(0);
            }
        });

        return view;
    }

    private void setNetworkAdapter(int countryChoiceSpinnerPosition) {
        ArrayAdapter<String> networkAdapter;
        List<List<String>> networkList = NetworkListRepository.getNetworkList();

        String[] networks = networkList.get(countryChoiceSpinnerPosition).toArray(new
                String[networkList.get(countryChoiceSpinnerPosition).size()]);
        networkAdapter = new ArrayAdapter<>(getActivity(), android
                .R.layout.simple_spinner_item, networks);

        networkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        networkChoiceSpinner.setAdapter(networkAdapter);
    }

    @OnTouch(R.id.quick_recharge_phone_text)
    boolean selectContact(View v, MotionEvent motionEvent) {

        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (motionEvent.getRawX() >= (quickRechargePhoneText.getRight() - quickRechargePhoneText.getCompoundDrawables()
                    [DRAWABLE_RIGHT].getBounds().width())) {

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest
                        .permission.READ_CONTACTS) != PackageManager
                        .PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]
                            {Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                } else {
                    launchContactPickerIntent();
                }

                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    launchContactPickerIntent();
                } else {
                    Snackbar.make(quickRechargeAmountLayout, "You need to " +
                            "enable this permission to select from " +
                            "your contacts", Snackbar.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONTACT_PICKER_RESULT) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contactData = data.getData();
                String number = "";
                Cursor cursor = getActivity().getContentResolver().query
                        (contactData, null, null, null, null);
                cursor.moveToFirst();
                String hasPhone = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                if (hasPhone.equals("1")) {
                    Cursor phones = getActivity().getContentResolver().query
                            (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                            + " = " + contactId, null, null);
                    while (phones.moveToNext()) {
                        number = phones.getString(phones.getColumnIndex
                                (ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("[-() ]", "");
                    }
                    phones.close();
                    //Do something with number
                    quickRechargePhoneText.setText(PhoneNumberConverter.convert(number));
                } else {
                    Toast.makeText(getActivity(), "This contact has no phone number",
                            Toast.LENGTH_LONG).show();
                }
                cursor.close();
            }
        }
    }

    @OnClick(R.id.quick_recharge_button)
    void processRecharge() {

        phoneNumber = quickRechargePhoneText.getText().toString();
        networkProvider = networkChoiceSpinner.getSelectedItem().toString();
        String amountText = quickRechargeAmountText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (amountText.isEmpty()) {
            quickRechargeAmountLayout.setError(getString(R.string.amount_empty_error));
            focusView = quickRechargeAmountLayout;
            cancel = true;
        } else if (Integer.valueOf(amountText) < 50) {
            quickRechargeAmountLayout.setError(getString(R.string.amount_low_error));
            focusView = quickRechargeAmountLayout;
            cancel = true;
        } else {
            amount = Integer.valueOf(quickRechargeAmountText.getText().toString());
            quickRechargeAmountLayout.setError(null);
        }

        if (phoneNumber.isEmpty()) {
            quickRechargePhoneLayout.setError(getString(R.string.phone_empty_error));
            focusView = quickRechargePhoneLayout;
            cancel = true;
        } else {
            quickRechargePhoneLayout.setError(null);
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            displayQuickRechargeOptionsDialog();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    private void launchContactPickerIntent() {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }

    private void displayQuickRechargeOptionsDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setCancelable(false)
                .setTitle("Recharge Options")
                .setMessage("Please select your preferred payment method.")
                .setPositiveButton("Wallet", (dialog1, id) -> {
                    if (!InternetConnectivity.isDeviceConnected(getActivity())) {
                        Snackbar.make(quickRechargeAmountLayout, R.string.internet_error,
                                Snackbar.LENGTH_SHORT).show();
                    } else {
                        boolean isCardTransaction = false;
                        performQuickRecharge(isCardTransaction);
                    }
                })
                .setNegativeButton("Card", (dialog12, which) -> {
                    switchToPaymentActivity();
                });

        final AlertDialog alert = dialog.create();
        alert.show();
    }

    private void switchToPaymentActivity() {
        Intent paymentActivityIntent = new Intent(getActivity(),
                CustomerQuickRechargePaymentActivity.class);

        paymentActivityIntent.putExtra("phone_number", phoneNumber);
        paymentActivityIntent.putExtra("amount", amount);
        paymentActivityIntent.putExtra("country", countryChoiceSpinner.getSelectedItem().toString
                ());
        paymentActivityIntent.putExtra("network_provider", networkProvider);
        paymentActivityIntent.putExtra("email", getCustomerEmail());
        startActivity(paymentActivityIntent);
    }

    private void performQuickRecharge(boolean isCardTransaction) {
        quickRechargeContainer.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        CustomerQuickRechargeRequest walletQuickRechargeRequest = new
                CustomerQuickRechargeRequest(phoneNumber, amount, networkProvider,
                isCardTransaction, getCustomerEmail());

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        CustomerService customerService = retrofit.create(CustomerService.class);
        final Observable<CustomerQuickRechargeResponse> quickRecharge =
                customerService.performCustomerQuickRechargeFromWallet
                        ("sMHuflOVZYEuCvXW1SGWmIMoj0aV5q1D", "oZFLQVYpRO9Ur8i6H8Q1J5c3RMgt0fb0",
                                walletQuickRechargeRequest);

        quickRecharge.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<CustomerQuickRechargeResponse>() {

                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                        quickRechargeContainer.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showResultDialog(TRANSACTION_ERROR_MESSAGE, TRANSACTION_FAILED);
                    }

                    @Override
                    public void onNext(CustomerQuickRechargeResponse quickRechargeResponse) {

                        if (quickRechargeResponse.getStatus() == 1) {
                            showRechargeSuccessfulDialog();
                            clearInputFields();
                        } else if (quickRechargeResponse.getStatus() == 0) {
                            showResultDialog(quickRechargeResponse.getData().getMessage(),
                                    TRANSACTION_FAILED);
                        }
                    }
                });
    }

    private String getCustomerEmail() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_customer_email_address), "");
    }

    private void showResultDialog(String message, int status) {
        FragmentManager fragmentManager = getChildFragmentManager();
        successfulFragment = RechargeResponseFragment.newInstance(message, status);
        successfulFragment.show(fragmentManager, "");
    }

    private void showRechargeSuccessfulDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.recharge_successful_layout, null);

        TextView textView = (TextView) dialogView.findViewById(R.id.success_message);
        textView.setText(TRANSACTION_SUCCESSFUL_MESSAGE);

        dialog.setCancelable(false)
                .setView(dialogView)
                .setPositiveButton("OK", (dialog1, id) -> {
                    Intent dashboardIntent = new Intent(getActivity(), CustomerActivity.class);
                    startActivity(dashboardIntent);
                });

        final AlertDialog alert = dialog.create();
        alert.show();
    }

    private void clearInputFields() {
        quickRechargePhoneText.setText("");
        networkChoiceSpinner.setSelection(0);
        quickRechargeAmountText.setText("");
    }

}
