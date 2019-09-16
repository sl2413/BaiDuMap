package com.shenl.BaiDuMap.application;

import com.shenl.map.application.MapApp;

public class MyApp extends MapApp {

    @Override
    public void onCreate() {
        super.onCreate();
        MapApp.setApplication(this);
    }
}
