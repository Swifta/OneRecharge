package com.swifta.onerecharge.resetagentpassword;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.swifta.onerecharge.util.AgentService;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by moyinoluwa on 11/28/16.
 */

public class ResetAgentPasswordPresenter implements ResetAgentPasswordContract.Presenter {

    private Retrofit retrofit;
    private ResetAgentPasswordContract.View mView;
    private SharedPreferences mSharedPreferences;
    private static final String EMAIL_ADDRESS_ID = "saved_email_address";
    private static final String AUTH_TOKEN_ID = "saved_auth_token";

    @NonNull
    private CompositeSubscription mSubscription = new CompositeSubscription();

    @Inject
    public ResetAgentPasswordPresenter(Retrofit retrofit, ResetAgentPasswordContract.View view,
                                       SharedPreferences sharedPreferences) {
        this.retrofit = retrofit;
        this.mView = view;
        this.mSharedPreferences = sharedPreferences;
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }

    @Override
    public String getStoredEmailAddress(String emailId) {
        return mSharedPreferences.getString(emailId, "null");
    }

    @Override
    public String getStoredAuthToken(String authId) {
        return mSharedPreferences.getString(authId, "null");
    }

    @Override
    public void submitPasswordRequest(String oldPassword, String newPassword) {

        RequestPasswordData passwordData = new RequestPasswordData
                (getStoredEmailAddress(EMAIL_ADDRESS_ID), getStoredAuthToken(AUTH_TOKEN_ID),
                        oldPassword, newPassword);

        mSubscription.clear();
        Subscription subscription = retrofit.create(AgentService.class)
                .changePassword(passwordData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AgentPassword>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideProgressBar();
                        mView.showPasswordLayout();
                        mView.showErrorMessage();
                    }

                    @Override
                    public void onNext(AgentPassword agentPassword) {
                        mView.hideProgressBar();
                        mView.showPasswordLayout();

                        if (agentPassword.getStatus() == 1) {
                            mView.showEmptyTextFields();
                        }

                        mView.showSuccessfullyChangedMessage(agentPassword.getData()
                                .getMessage());
                    }
                });

        mSubscription.add(subscription);
    }
}