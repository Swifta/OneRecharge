package com.swifta.onerecharge.agentquicktransactionhistory;


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
import com.swifta.onerecharge.util.SparkCustomAdapter;
import com.swifta.onerecharge.util.Url;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
public class AgentQuickTransactionHistoryFragment extends Fragment {
    @BindView(R.id.quick_recharge_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.sparkview)
    SparkView sparkView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private SharedPreferences sharedPreferences;
    private QuickRechargeHistoryAdapter quickRechargeHistoryAdapter;
    public List<Row> rowList;

    public AgentQuickTransactionHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agent_quick_transaction_history, container,
                false);
        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle
                (getResources().getString(R.string.quick_recharge_history));

        sharedPreferences = getActivity().getSharedPreferences(getString(R
                .string.agent_shared_preference_name), Context.MODE_PRIVATE);

        getRowList();

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setVisible(false);
        item = menu.findItem(R.id.action_logout);
        item.setVisible(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sort_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_date:
                quickRechargeHistoryAdapter.swapItems(QuickRechargeHistoryRepository.getHistoryListSortedByDate());
                return true;
            case R.id.sort_by_recipient:
                quickRechargeHistoryAdapter.swapItems(QuickRechargeHistoryRepository.getHistoryListSortedByRecipient());
                return true;
            case R.id.sort_by_network:
                quickRechargeHistoryAdapter.swapItems(QuickRechargeHistoryRepository.getHistoryListSortedByNetwork());
                return true;
            case R.id.sort_by_amount:
                quickRechargeHistoryAdapter.swapItems(QuickRechargeHistoryRepository.getHistoryListSortedByAmount());
                return true;
            case R.id.sort_by_status:
                quickRechargeHistoryAdapter.swapItems(QuickRechargeHistoryRepository.getHistoryListSortedByStatus());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getRowList() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        final AgentService agentService = retrofit
                .create(AgentService.class);
        final Observable<AgentQuickTransactionHistory> agent = agentService
                .getQuickTransactionHistory("*", "*", "*", "*", "*", "*", getEmailAddress(), 1,
                        20, "");

        agent.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AgentQuickTransactionHistory>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(AgentQuickTransactionHistory agentQuickTransactionHistory) {
                        if (agentQuickTransactionHistory.getRows() != null) {
                            rowList = agentQuickTransactionHistory.getRows();

                            QuickRechargeHistoryRepository.setOriginalHistoryList(rowList);

                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            sparkView.setVisibility(View.VISIBLE);

                            float[] amountArray = new float[rowList.size()];
                            int amountArrayIndex = 0;

                            for (int i = rowList.size() - 1; i >= 0; i--) {
                                amountArray[amountArrayIndex] = rowList.get(i).getAmount();
                                amountArrayIndex++;
                            }

                            sparkView.setAdapter(new SparkCustomAdapter(amountArray));

                            quickRechargeHistoryAdapter = new QuickRechargeHistoryAdapter
                                    (QuickRechargeHistoryRepository.getHistoryListSortedByDate());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(quickRechargeHistoryAdapter);
                        }
                    }
                });
    }

    private String getEmailAddress() {
        String email = sharedPreferences.getString(getResources().getString(R.string
                .saved_email_address), "");

        return email;
    }
}