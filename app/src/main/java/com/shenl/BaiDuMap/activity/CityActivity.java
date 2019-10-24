package com.shenl.BaiDuMap.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.shenl.BaiDuMap.R;
import com.shenl.map.fragment.CityFragment;

public class CityActivity extends FragmentActivity {

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
        Fragment_Secter(R.id.rl_CityContent,new CityFragment());
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
}
