package com.shenl.map.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddressDao {

    private static String codeSql = "select district_name,parent_id,region_code from sys_district where parent_id=? order by region_code";
    private static String nameSql = "select district_name,parent_id,region_code from sys_district where parent_id like ? order by region_code";

    /**
     * TODO 功能：通过行政区划获取下级城市列表
     * <p>
     * 参数说明:code:行政区划码，为"0"(或者为空)时获取省级数据
     * 作    者:   沈  亮
     * 创建时间:   2019/10/24
     */
    public static void getAddressForCode(Context context, String code, CallBack callBack) {
        code = TextUtils.isEmpty(code) ? "0" : code;
        List<Bean> list = new ArrayList<>();
        File file = new File(context.getFilesDir(), "address.db");
        SQLiteDatabase database = SQLiteDatabase.openDatabase(file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
        //查询数据库的操作
        Cursor cursor = database.rawQuery(codeSql, new String[]{code});
        //解析cursor
        while (cursor.moveToNext()) {
            Bean bean = new Bean(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            list.add(bean);
        }
        cursor.close();
        database.close();
        callBack.Finish(list);
    }

    /**
     * TODO 功能：通过行政区划获取下级城市列表
     * <p>
     * 参数说明:code:行政区划码，为"0"(或者为空)时获取省级数据
     * 作    者:   沈  亮
     * 创建时间:   2019/10/24
     */
    public static void getAddressForName(Context context, String name, CallBack callBack) {
        List<Bean> list = new ArrayList<>();
        File file = new File(context.getFilesDir(), "address.db");
        SQLiteDatabase database = SQLiteDatabase.openDatabase(file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
        //查询数据库的操作
        Cursor cursor = database.rawQuery(nameSql, new String[]{"%" + name + "%"});
        //解析cursor
        while (cursor.moveToNext()) {
            Bean bean = new Bean(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            list.add(bean);
        }
        cursor.close();
        database.close();
        callBack.Finish(list);
    }


    /*
     * 选择城市所需实体类
     * */
    public static class Bean {

        public Bean(String districtName, String parentId, String regionCode) {
            this.districtName = districtName;
            this.parentId = parentId;
            this.regionCode = regionCode;
        }

        private String districtName;
        private String parentId;
        private String regionCode;

        public String getDistrictName() {
            return districtName;
        }

        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getRegionCode() {
            return regionCode;
        }

        public void setRegionCode(String regionCode) {
            this.regionCode = regionCode;
        }
    }

    /**
     * TODO 功能：获取位置所需回调函数
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/10/26
     */
    public interface CallBack {
        void Finish(List<Bean> list);
    }
}
