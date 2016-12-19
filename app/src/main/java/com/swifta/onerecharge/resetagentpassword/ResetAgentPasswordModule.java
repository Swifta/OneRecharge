package com.swifta.onerecharge.resetagentpassword;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.swifta.onerecharge.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by moyinoluwa on 12/13/16.
 */

@Module
public class ResetAgentPasswordModule {

    private String mBaseUrl;

    private final ResetAgentPasswordContract.View mView;

    public ResetAgentPasswordModule(String baseUrl, ResetAgentPasswordContract.View view) {
        this.mBaseUrl = baseUrl;
        this.mView = view;
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit() {
        final Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mBaseUrl)
                .build();

        return retrofit;
    }

    @Provides
    @Singleton
    ResetAgentPasswordContract.View providesResetAgentPasswordContractView() {
        return mView;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return application.getSharedPreferences(application.getString(R.string
                .agent_shared_preference_name), Context.MODE_PRIVATE);
    }
}
