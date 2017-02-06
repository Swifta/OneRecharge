package com.swifta.onerecharge.agent.agentscheduledtransactionhistory;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.robinhood.spark.SparkView;
import com.swifta.onerecharge.R;
import com.swifta.onerecharge.util.AgentService;
import com.swifta.onerecharge.util.InternetConnectivity;
import com.swifta.onerecharge.util.SparkCustomAdapter;
import com.swifta.onerecharge.util.Url;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
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
public class AgentScheduledTransactionHistoryFragment extends Fragment {
    @BindView(R.id.scheduled_recharge_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.sparkview)
    SparkView sparkView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private SharedPreferences sharedPreferences;
    private ScheduledRechargeHistoryAdapter scheduledRechargeHistoryAdapter;
    public List<Row> rowList;

    private Realm realm;

    public AgentScheduledTransactionHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agent_scheduled_transaction_history, container,
                false);
        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle
                (getResources().getString(R.string.schedule_history));

        sharedPreferences = getActivity().getSharedPreferences(getString(R
                .string.agent_shared_preference_name), Context
                .MODE_PRIVATE);
        realm = Realm.getDefaultInstance();

        populateViewWithSavedData();

        pullUpdatedDataWitheInternetConnection();

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_settings);
        setMenuItemToFalse(item);

        item = menu.findItem(R.id.action_logout);
        setMenuItemToFalse(item);

        item = menu.findItem(R.id.action_privacy_policy);
        setMenuItemToFalse(item);

        item = menu.findItem(R.id.sort_by_date);
        item.setVisible(true);

        item = menu.findItem(R.id.sort_by_recipient);
        item.setVisible(true);

        item = menu.findItem(R.id.sort_by_amount);
        item.setVisible(true);

        item = menu.findItem(R.id.sort_by_network);
        item.setVisible(true);

        item = menu.findItem(R.id.sort_by_status);
        item.setVisible(true);
    }

    private void setMenuItemToFalse(MenuItem item) {
        if (item != null) {
            item.setVisible(false);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sort_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        List<RealmRow> list;

        switch (item.getItemId()) {
            case R.id.sort_by_date:
                list = ScheduledRechargeHistoryRepository.getHistoryListSortedByDate();
                swapAdapterItems(list);
                return true;
            case R.id.sort_by_recipient:
                list = ScheduledRechargeHistoryRepository.getHistoryListSortedByRecipient();
                swapAdapterItems(list);
                return true;
            case R.id.sort_by_network:
                list = ScheduledRechargeHistoryRepository.getHistoryListSortedByNetwork();
                swapAdapterItems(list);
                return true;
            case R.id.sort_by_amount:
                list = ScheduledRechargeHistoryRepository.getHistoryListSortedByAmount();
                swapAdapterItems(list);
                return true;
            case R.id.sort_by_status:
                list = ScheduledRechargeHistoryRepository.getHistoryListSortedByStatus();
                swapAdapterItems(list);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void swapAdapterItems(List<RealmRow> list) {
        if (list.size() == 0 || scheduledRechargeHistoryAdapter == null) {
            Toast.makeText(getActivity(), "The list is currently empty. Please try again later.",
                    Toast.LENGTH_SHORT).show();
        } else {
            scheduledRechargeHistoryAdapter.swapItems(list);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void getRowList() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        final AgentService agentService = retrofit
                .create(AgentService.class);
        final Observable<AgentScheduledTransactionHistory> agent = agentService
                .getScheduledTransactionHistory("*", "*", "*", "*", "*", "*", getEmailAddress(), 1,
                        20, "");

        agent.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AgentScheduledTransactionHistory>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isAdded()) {
                            Toast.makeText(getActivity(), e.getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNext(AgentScheduledTransactionHistory agentScheduledTransactionHistory) {
                        if (agentScheduledTransactionHistory.getRows() != null) {
                            rowList = agentScheduledTransactionHistory.getRows();
                            List<RealmRow> realmRowList = new ArrayList<RealmRow>();

                            for (Row r : rowList) {
                                RealmRow realmRow = new RealmRow();

                                realmRow.setId(r.getId());
                                realmRow.setUserId(r.getUserId());
                                realmRow.setUserToken(r.getUserToken());
                                realmRow.setAmount(r.getAmount());
                                realmRow.setRecipient(r.getRecipient());
                                realmRow.setNetwork(r.getNetwork());
                                realmRow.setGoTime(r.getGoTime());
                                realmRow.setV(r.getV());
                                realmRow.setTransactionId(r.getTransactionId());
                                realmRow.setCreatedTime(r.getCreatedTime());
                                realmRow.setCancelled(r.getCancelled());
                                realmRow.setSuccessful(r.getSuccessful());

                                realmRowList.add(realmRow);
                            }

                            clearPreviousRealmData();
                            saveDataToRealm(realmRowList);
                            displayTransactions(realmRowList);
                        }
                    }
                });
    }

    private void clearPreviousRealmData() {
        realm.executeTransaction(realm -> realm.delete(RealmRow.class));
    }

    private void saveDataToRealm(List<RealmRow> rowList) {
        realm.executeTransaction(realm -> {
            for (RealmRow realmRow : rowList) {
                realm.copyToRealm(realmRow);
            }
        });
    }

    private void populateViewWithSavedData() {
        if (!getRowListFromRealm().isEmpty()) {
            List<RealmRow> rows = new ArrayList<RealmRow>();

            for (RealmRow r : getRowListFromRealm()) {
                rows.add(r);
            }

            displayTransactions(rows);
        }
    }

    private void pullUpdatedDataWitheInternetConnection() {
        if (InternetConnectivity.isDeviceConnected(getActivity())) {
            getRowList();
        }
    }

    private RealmResults<RealmRow> getRowListFromRealm() {
        return realm.where(RealmRow.class).findAll();
    }

    private void displayTransactions(List<RealmRow> row) {

        ScheduledRechargeHistoryRepository.setOriginalHistoryList(row);

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        sparkView.setVisibility(View.VISIBLE);

        float[] amountArray = new float[row.size()];
        int amountArrayIndex = 0;

        for (int i = row.size() - 1; i >= 0; i--) {
            amountArray[amountArrayIndex] = row.get(i).getAmount();
            amountArrayIndex++;
        }

        sparkView.setAdapter(new SparkCustomAdapter(amountArray));

        scheduledRechargeHistoryAdapter = new ScheduledRechargeHistoryAdapter(
                ScheduledRechargeHistoryRepository.getHistoryListSortedByDate());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(scheduledRechargeHistoryAdapter);
    }

    private String getEmailAddress() {
        String email = sharedPreferences.getString(getResources().getString(R.string
                .saved_email_address), "");

        return email;
    }
}
