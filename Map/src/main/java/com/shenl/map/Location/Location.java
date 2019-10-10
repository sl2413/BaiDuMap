package com.shenl.map.Location;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.shenl.map.CallBack.LocationListener;

import java.util.HashMap;
import java.util.Map;


public class Location {

    private static Map<Integer, String> errorMap;

    /**
     * TODO 功能：获取基础定位
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/9/4
     */
    public static void getLocation(Context context, final LocationListener listener) {
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        LocationClient locationClient = new LocationClient(context);
//声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();
//注册监听函数
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                //61 ： GPS定位结果，GPS定位成功。
                //161： 网络定位结果，网络定位定位成功。
                //65 ： 定位缓存的结果。
                //66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果。
                int Code = location.getLocType();
                if (61 == Code || 161 == Code || 65 == Code || 66 == Code) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("Longitude",location.getLongitude()+"");
                    map.put("Latitude",location.getLatitude()+"");
                    map.put("AddrStr",location.getAddrStr());
                    map.put("Province",location.getProvince());
                    map.put("City",location.getCity());
                    map.put("District",location.getDistrict());
                    map.put("Street",location.getStreet());
                    map.put("StreetNumber",location.getStreetNumber());
                    listener.success(map);
                } else {
                    listener.error(getErrorStr(Code));
                }
            }
        });
//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("gcj02");
//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(1000);
//可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
//可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
//可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(false);
//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(true);
//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true);
//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true);
//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true);
//可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false);
//可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);
//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(false);
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        locationOption.setOpenAutoNotifyMode();
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        locationOption.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        locationClient.setLocOption(locationOption);
//开始定位
        locationClient.start();
    }

    /**
     * TODO 功能：获取定位失败信息
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/9/4
     */
    public static String getErrorStr(int errorCode) {
        String errorMsg = "key验证失败，请按照说明文档重新申请KEY。";
        if (errorMap == null) {
            errorMap = new HashMap<Integer, String>();
            errorMap.put(62, "62:无法获取有效定位依据，定位失败，请检查运营商网络或者wifi网络是否正常开启，尝试重新请求定位。");
            errorMap.put(63, "63:网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位。");
            errorMap.put(67, "67:离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果。");
            errorMap.put(68, "68:网络连接失败时，查找本地离线定位时对应的返回结果。");
            errorMap.put(162, "162:请求串密文解析失败。");
            errorMap.put(167, "167:服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。");
            errorMap.put(502, "502:key参数错误，请按照说明文档重新申请KEY。");
            errorMap.put(505, "505:key不存在或者非法，请按照说明文档重新申请KEY。");
            errorMap.put(601, "601:key服务被开发者自己禁用，请按照说明文档重新申请KEY。");
            errorMap.put(602, "601:key mcode不匹配，您的ak配置过程中安全码设置有问题，请确保：sha1正确，“;”分号是英文状态；且包名是您当前运行应用的包名，请按照说明文档重新申请KEY。");
        }
        if (!"".equals(errorMap.get(errorCode))) {
            errorMsg = errorMap.get(errorCode);
        }
        return errorMsg;
    }
}
