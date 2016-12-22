package com.swifta.onerecharge.agentdashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.swifta.onerecharge.R;
import com.swifta.onerecharge.agentapikey.AgentApiKeyResponse;
import com.swifta.onerecharge.networklist.NetworkListRepository;
import com.swifta.onerecharge.networklist.NetworkListResponse;
import com.swifta.onerecharge.util.AgentService;
import com.swifta.onerecharge.util.Url;

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
public class DashboardFragment extends Fragment {
    private SharedPreferences sharedPreferences;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container,
                false);

        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle
                (getResources().getString(R.string.app_name));

        sharedPreferences = getActivity().getSharedPreferences(getString(R
                .string.agent_shared_preference_name), Context.MODE_PRIVATE);

        setupViewPager(viewPager, getActivity().getSupportFragmentManager());

        initTabInstances();
        tabLayout.setupWithViewPager(viewPager);

        setupViewPager(viewPager, getChildFragmentManager());

        getAgentApiKey();

        getNetworkList();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupViewPager(viewPager, getChildFragmentManager());
    }

    private void setupViewPager(ViewPager viewPager, FragmentManager fragmentManager) {
        PagerAdapter adapter = new PagerAdapter(getActivity(), fragmentManager);
        adapter.addFragment(new ProfileRulesFragment(), getResources().getString(R
                .string.profile_rules));
        adapter.addFragment(new ProfileSummaryFragment(), getResources().getString(R
                .string.profile_summary));
        viewPager.setAdapter(adapter);
    }

    private void initTabInstances() {
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString
                (R.string.profile_rules)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString
                (R.string.profile_summary)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private String getEmailAddress() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_email_address), "");
    }

    private String getToken() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_auth_token), "");
    }

    private void getNetworkList() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        final AgentService agentService = retrofit
                .create(AgentService.class);
        final Observable<NetworkListResponse> agent = agentService.getNetworkList();

        agent.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<NetworkListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(NetworkListResponse networkListResponse) {
                        if (networkListResponse.getStatus() == 1) {
                            NetworkListRepository.setNetworkList(networkListResponse.getData());
                        }
                    }
                });
    }

    private void getAgentApiKey() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        final AgentService agentService = retrofit
                .create(AgentService.class);
        final Observable<AgentApiKeyResponse> agent = agentService.getAgentApiKey(getEmailAddress
                (), getToken());

        agent.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AgentApiKeyResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(AgentApiKeyResponse agentApiKeyResponse) {
                        if (agentApiKeyResponse.getStatus() == 1) {
                            saveApiAgentKey(agentApiKeyResponse.getData());
                        }
                    }
                });
    }

    private void saveApiAgentKey(String agentApiKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.saved_agent_api_key), agentApiKey);

        editor.apply();
    }
}
