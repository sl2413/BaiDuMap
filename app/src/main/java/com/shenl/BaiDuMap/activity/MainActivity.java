package com.shenl.BaiDuMap.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.shenl.BaiDuMap.R;
import com.shenl.map.CallBack.LocationListener;
import com.shenl.map.Location.Location;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * TODO 功能：获取基础坐标
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/9/4
     */
    public void getLocation(View v) {
        Location.getLocation(MainActivity.this, new LocationListener() {
            @Override
            public void success(String s) {
                Log.e("shenl",s);
            }

            @Override
            public void error(String error) {
                Log.e("shenl",error);
            }
        });
    }

    /**
     * TODO 功能：显示百度地图
     *
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/9/4
     */
    public void ShowMap(View v){
        Intent intent = new Intent(MainActivity.this,MapActivity.class);
        startActivity(intent);
    }
}
