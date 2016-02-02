package com.example.perloti.contacts;

import android.app.Application;
import android.util.LruCache;

import com.example.perloti.contacts.util.ApplicationUtil;

/**
 * Created by Perloti on 30/01/2016.
 */


public class ApplicationManager extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationUtil.setContext(getApplicationContext());
    }

}

