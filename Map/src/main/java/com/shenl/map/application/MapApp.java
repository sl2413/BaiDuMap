package com.shenl.map.application;

import android.app.Application;
import android.support.annotation.NonNull;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

public class MapApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setApplication(this);
    }

    /**
     * TODO 功能：当主工程没有继承本Application时，可以使用setApplication方法初始化Application
     * <p>
     * 参数说明:application为主工程的application
     * 作    者:   沈  亮
     * 创建时间:   2019/9/16
     */
    public static synchronized void setApplication(@NonNull Application application) {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //SDKInitializer.initialize(application);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        //SDKInitializer.setCoordType(CoordType.BD09LL);
    }
}
