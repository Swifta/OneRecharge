package com.swifta.onerecharge;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

/**
 * Created by moyinoluwa on 9/19/16.
 */
public class OneRechargeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidThreeTen.init(this);
    }
}
