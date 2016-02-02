package com.example.perloti.contacts.util;

import android.content.Context;

/**
 * Created by Perloti on 30/01/2016.
 */
public class ApplicationUtil {

    private static Context context;

    private ApplicationUtil(){
        super();
    }

    public static void  setContext(Context context){
        ApplicationUtil.context = context;
    }

    public static Context getContext(){
        return ApplicationUtil.context;
    }




}
