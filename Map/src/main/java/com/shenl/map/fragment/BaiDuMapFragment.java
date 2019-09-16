package com.shenl.map.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.shenl.map.CallBack.LocationListener;
import com.shenl.map.Location.Location;
import com.shenl.map.R;

import org.json.JSONException;
import org.json.JSONObject;

public class BaiDuMapFragment extends Fragment {

    private MapView mv_map;
    private BaiduMap map;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, null);
        initView(view);
        initData();
        initEvent();
        return view;
    }

    private void initView(View view) {
        mv_map = view.findViewById(R.id.mv_map);
    }

    private void initData() {
        map = mv_map.getMap();
        //MAP_TYPE_NORMAL 普通地图 (默认)
        //MAP_TYPE_SATELLITE 显示卫星图层
        //MAP_TYPE_NONE 空白地图
        //map.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        map.setMyLocationEnabled(true);

        MyLocationData locData = new MyLocationData.Builder()
                .latitude(39.854462)
                .longitude(119.409352).build();
        map.setMyLocationData(locData);
    }

    private void initEvent() {

    }

    /**
     * TODO 功能：获取json中任意字段值
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/9/4
     */
    public static String getFieldValue(String json, String key) {
        if (TextUtils.isEmpty(json))
            return null;
        if (!json.contains(key))
            return "";
        JSONObject jsonObject = null;
        String value = null;
        try {
            jsonObject = new JSONObject(json);
            value = jsonObject.getString(key);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时必须调用mMapView. onResume ()
        mv_map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时必须调用mMapView. onPause ()
        mv_map.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时必须调用mMapView.onDestroy()
        mv_map.onDestroy();
    }
}
