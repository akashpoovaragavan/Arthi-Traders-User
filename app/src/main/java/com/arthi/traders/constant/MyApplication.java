package com.arthi.traders.constant;


import android.app.Application;
import android.content.Context;

import com.google.firebase.messaging.FirebaseMessaging;

public class MyApplication extends Application {
    public static Context ctx;
    @Override
    public void onCreate() {
        super.onCreate();
        ctx = this;
        VolleySingleton.handleSSLHandshake();
        FirebaseMessaging.getInstance().subscribeToTopic("ats");

    }

}
