package com.swifta.onerecharge.agent.agentscheduledrecharge;

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
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.swifta.onerecharge.R;
import com.swifta.onerecharge.agent.agentquickrecharge.RechargeResponseFragment;
import com.swifta.onerecharge.agent.agentscheduledrecharge.scheduledrechargerequestmodel.Schedule;
import com.swifta.onerecharge.agent.agentscheduledrecharge.scheduledrechargerequestmodel.ScheduledRechargeRequest;
import com.swifta.onerecharge.agent.agentscheduledrecharge.scheduledrechargeresponsemodel.ScheduledRechargeResponse;
import com.swifta.onerecharge.networklist.NetworkListRepository;
import com.swifta.onerecharge.util.AgentService;
import com.swifta.onerecharge.util.InternetConnectivity;
import com.swifta.onerecharge.util.PhoneNumberConverter;
import com.swifta.onerecharge.util.Url;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
public class ScheduledRechargeFragment extends Fragment {
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.scheduled_recharge_phone_text)
    TextInputEditText scheduledRechargePhoneText;
    @BindView(R.id.scheduled_recharge_phone_layout)
    TextInputLayout scheduledRechargePhoneLayout;
    @BindView(R.id.network_spinner)
    Spinner networkChoiceSpinner;

    @BindView(R.id.scheduled_recharge_amount)
    TextInputEditText scheduledRechargeAmountText;
    @BindView(R.id.scheduled_recharge_amount_layout)
    TextInputLayout scheduledRechargeAmountLayout;

    @BindView(R.id.time_picker)
    Button timePickerButton;
    @BindView(R.id.date_picker)
    Button datePickerButton;

    @BindView(R.id.recharge_container)
    LinearLayout linearLayout;

    private SharedPreferences sharedPreferences;
    RechargeResponseFragment successfulFragment;
    TimePickerFragment timePickerFragment;
    DatePickerFragment datePickerFragment;

    String phoneNumber;
    Integer amount;
    String networkProvider;
    String date;
    String time;

    List<Schedule> scheduleList = new ArrayList<>();

    private static final int TRANSACTION_FAILED = 0;
    private static final int TRANSACTION_SUCCESSFUL = 1;
    private static final String TRANSACTION_SUCCESSFUL_MESSAGE = "Transaction sent successfully!";
    private static final String TRANSACTION_ERROR_MESSAGE = "Transaction " +
            "unsuccessful. Please try again.";

    private static final int CONTACT_PICKER_RESULT = 200;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 201;

    public ScheduledRechargeFragment() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scheduled_recharge,
                container, false);

        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle
                (getResources().getString(R.string.scheduled_recharge));

        sharedPreferences = getActivity().getSharedPreferences(getString(R
                .string.agent_shared_preference_name), Context.MODE_PRIVATE);

        timePickerFragment = TimePickerFragment.getInstance();
        timePickerFragment.setSelectedTimeListener(time1 -> setTimeButtonText(time1));

        datePickerFragment = DatePickerFragment.getInstance();
        datePickerFragment.setSelectedDateListener(date1 -> setDateButtonText(date1));

        ArrayAdapter<String> adapter;
        if (NetworkListRepository.getNetworkList() != null) {
            List<String> networkList = NetworkListRepository.getNetworkList();
            String[] networks = networkList.toArray(new String[networkList.size()]);

            adapter = new ArrayAdapter<>(getActivity(), android
                    .R.layout.simple_spinner_item, networks);
        } else {
            String[] networks = {"Airtel", "Etisalat", "Glo", "MTN"};
            adapter = new ArrayAdapter<>(getActivity(), android
                    .R.layout.simple_spinner_item, networks);
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        networkChoiceSpinner.setAdapter(adapter);

        return view;
    }

    @OnTouch(R.id.scheduled_recharge_phone_text)
    boolean selectContact(View v, MotionEvent motionEvent) {

        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (motionEvent.getRawX() >= (scheduledRechargePhoneText.getRight() - scheduledRechargePhoneText.getCompoundDrawables()
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    launchContactPickerIntent();
                } else {
                    Toast.makeText(getActivity(), "You need to" +
                            "enable this permission to select from " +
                            "your contacts", Toast.LENGTH_SHORT).show();
                }
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
                    scheduledRechargePhoneText.setText(PhoneNumberConverter.convert(number));
                } else {
                    Toast.makeText(getActivity(), "This contact has no phone number",
                            Toast.LENGTH_LONG).show();
                }
                cursor.close();
            }
        }
    }

    @OnClick(R.id.date_picker)
    void selectDate() {
        datePickerFragment.show(getChildFragmentManager(), "datepicker");
    }

    @OnClick(R.id.time_picker)
    void selectTime() {
        timePickerFragment.show(getChildFragmentManager(), "timepicker");
    }

    public void setTimeButtonText(String time) {
        timePickerButton.setText(time);
    }

    public void setDateButtonText(String date) {
        datePickerButton.setText(date);
    }

    @OnClick(R.id.scheduled_recharge_button)
    void processRecharge() {

        if (scheduledRechargePhoneLayout.getVisibility() != View.GONE) {
            phoneNumber = scheduledRechargePhoneText.getText().toString();
        }

        if (networkChoiceSpinner.getVisibility() != View.GONE) {
            networkProvider = networkChoiceSpinner.getSelectedItem()
                    .toString();
        }

        String amountText = scheduledRechargeAmountText.getText().toString();
        date = datePickerButton.getText().toString();
        time = timePickerButton.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (time.equals(getResources().getString(R
                .string.time))) {
            Toast.makeText(getActivity(), "Please select a time", Toast
                    .LENGTH_SHORT).show();
            focusView = timePickerButton;
            cancel = true;
        }

        if (date.equals(getResources().getString(R
                .string.date))) {
            Toast.makeText(getActivity(), "Please select a date", Toast
                    .LENGTH_SHORT).show();
            focusView = datePickerButton;
            cancel = true;
        }

        if (amountText.isEmpty()) {
            scheduledRechargeAmountLayout.setError(getString(R.string.amount_empty_error));
            focusView = scheduledRechargeAmountLayout;
            cancel = true;
        } else if (Integer.valueOf(amountText) < 50) {
            scheduledRechargeAmountLayout.setError(getString(R.string.amount_low_error));
            focusView = scheduledRechargeAmountLayout;
            cancel = true;
        } else {
            amount = Integer.valueOf(scheduledRechargeAmountText.getText().toString());
            scheduledRechargeAmountLayout.setError(null);
        }

        /** This block of code was commented out because I was only initially working with the Nigerian
         * usecase where the length of a telephone number is 11 digits. This might not hold again now
         * that we have Ghanaian numbers too **/
//        if (scheduledRechargePhoneLayout.getVisibility() != View.GONE) {
//            if (phoneNumber.isEmpty()) {
//                scheduledRechargePhoneLayout.setError(getString(R.string.phone_empty_error));
//                focusView = scheduledRechargePhoneLayout;
//                cancel = true;
//            } else if (phoneNumber.length() != 11) {
//                scheduledRechargePhoneLayout.setError(getString(R.string.phone_length_error));
//                focusView = scheduledRechargePhoneLayout;
//                cancel = true;
//            } else {
//                scheduledRechargePhoneLayout.setError(null);
//            }
//        }

        if (scheduledRechargePhoneLayout.getVisibility() != View.GONE) {
            if (phoneNumber.isEmpty()) {
                scheduledRechargePhoneLayout.setError(getString(R.string.phone_empty_error));
                focusView = scheduledRechargePhoneLayout;
                cancel = true;
            } else {
                scheduledRechargePhoneLayout.setError(null);
            }
        }

        if (!date.equals(getResources().getString(R
                .string.date)) && !time.equals(getResources().getString(R
                .string.time))) {

            String selectedDate = date + " " + time;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy " +
                    "HH:mm", Locale.getDefault());
            long selectedTimeMillis = 0L;
            try {
                Date simpleFormatDate = simpleDateFormat.parse(selectedDate);
                selectedTimeMillis = simpleFormatDate.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (System.currentTimeMillis() > selectedTimeMillis) {
                Toast.makeText(getActivity(), "Please select a date in the future",
                        Toast.LENGTH_SHORT).show();
                focusView = datePickerButton;
                cancel = true;
            }
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (!InternetConnectivity.isDeviceConnected(getActivity())) {
                Snackbar.make(scheduledRechargeAmountLayout, R.string.internet_error,
                        Snackbar.LENGTH_SHORT).show();
            } else {
                scheduleList.add(new Schedule(amount, date, time));
                progressBar.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                performScheduledRecharge();
            }
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

    private void performScheduledRecharge() {
        int total = 0;

        for (int i = 0; i < scheduleList.size(); i++) {
            total += scheduleList.get(i).amount;
        }

        ScheduledRechargeRequest scheduledRechargeRequest = new ScheduledRechargeRequest(
                phoneNumber, networkProvider, total, "", scheduleList);

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        AgentService agentService = retrofit.create(AgentService.class);

        final Observable<ScheduledRechargeResponse> scheduledRecharge =
                agentService.performAgentScheduledRecharge(getAgentAuthToken(), getAgentApiKey(),
                        scheduledRechargeRequest);

        scheduledRecharge.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ScheduledRechargeResponse>() {

                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showResultDialog(TRANSACTION_ERROR_MESSAGE, TRANSACTION_FAILED);
                    }

                    @Override
                    public void onNext(ScheduledRechargeResponse scheduledRechargeResponse) {

                        if (scheduledRechargeResponse.getStatus() == 1) {
                            showResultDialog(TRANSACTION_SUCCESSFUL_MESSAGE, TRANSACTION_SUCCESSFUL);
                            clearInputFields();
                        } else if (scheduledRechargeResponse.getStatus() == 0) {
                            showResultDialog(scheduledRechargeResponse
                                    .getData().getMessage(), TRANSACTION_FAILED);
                        }
                    }
                });
    }

    private String getAgentAuthToken() {
        return sharedPreferences.getString(getResources()
                .getString(R.string.saved_auth_token), "");
    }

    private String getAgentApiKey() {
        return sharedPreferences.getString(getResources()
                .getString(R.string.saved_agent_api_key), "");
    }

    private void showResultDialog(String message, int status) {
        FragmentManager fragmentManager = getChildFragmentManager();
        successfulFragment = RechargeResponseFragment.newInstance(message, status);
        successfulFragment.show(fragmentManager, "");
    }

    private void clearInputFields() {
        scheduledRechargePhoneText.setText("");
        networkChoiceSpinner.setSelection(0);
        scheduledRechargeAmountText.setText("");
        datePickerButton.setText(getResources().getString(R.string.date));
        timePickerButton.setText(getResources().getString(R.string.time));
        if (datePickerButton.getVisibility() == View.GONE) {
            datePickerButton.setVisibility(View.VISIBLE);
        }

        if (timePickerButton.getVisibility() == View.GONE) {
            datePickerButton.setVisibility(View.VISIBLE);
        }
    }
}