package com.shenl.BaiDuMap.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.shenl.BaiDuMap.R;
import com.shenl.map.fragment.CityFragment;

public class CityActivity extends FragmentActivity {

    private CityFragment city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        initView();
        initData();
        initEvent();
    }

    private void initView() {

    }

    private void initData() {
        city = new CityFragment();
        Fragment_Secter(R.id.rl_CityContent,city);
    }

    private void initEvent() {

    }
    /**
     * TODO 功能：fragment切换器
     *
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/9/4
     */
    public void Fragment_Secter(int res, Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction bt = fm.beginTransaction();
        bt.replace(res, fragment);
        bt.commit();
    }

    public void getAddress(View v){
        Log.e("shenl","获取地址");
//        Log.e("shenl",city.getAllAddress());
//        Log.e("shenl",city.getProvinceName()+city.getProvinceCode());
        Log.e("shenl",city.getCityName()+city.getCityCode());
//        Log.e("shenl",city.getAreaName()+city.getAreaCode());
    }
}
