package com.shenl.map.fragment;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.shenl.map.CallBack.LocationListener;
import com.shenl.map.Location.Location;
import com.shenl.map.R;
import com.shenl.map.dao.AddressDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


public class CityFragment extends Fragment {

    private ListView lv_province;
    private ListView lv_city;
    private ListView lv_area;
    private String allAddress = "";
    private List<AddressDao.Bean> provinceList;
    private List<AddressDao.Bean> cityList;
    private List<AddressDao.Bean> areaList;
    private String provinceName = "";
    private String cityName = "";
    private String areaName = "";
    private String provinceCode = "";
    private String cityCode = "";
    private String areaCode = "";
    private TextView tv_address;
    private MyAdapter cityAdapter;
    private MyAdapter areaAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        copyDB("address.db");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        initView(view);
        initData();
        initEvent();
        return view;
    }

    private void initView(View view) {
        lv_province = view.findViewById(R.id.lv_province);
        lv_city = view.findViewById(R.id.lv_city);
        lv_area = view.findViewById(R.id.lv_area);
        tv_address = view.findViewById(R.id.tv_address);
    }

    private void initData() {
        //联网自动获取定位地址
        Location.getLocation(getActivity(), new LocationListener() {
            @Override
            public void success(Map<String, String> map) {
                String city = map.get("City");
                //Log.e("shenl", city);
                AddressDao.getAddressForName(getActivity(), city, new AddressDao.CallBack() {
                    @Override
                    public void Finish(List<AddressDao.Bean> list) {
                        //Log.e("shenl", "~~~" + list.get(0).getDistrictName());
                        //Log.e("shenl", "~~~" + list.get(0).getRegionCode());
                        cityName = list.get(0).getDistrictName();
                        cityCode = list.get(0).getRegionCode();
                    }
                });
            }

            @Override
            public void error(String error) {

            }
        });
        //查询省级数据
        AddressDao.getAddressForCode(getActivity(), "0", new AddressDao.CallBack() {
            @Override
            public void Finish(List<AddressDao.Bean> list) {
                provinceList = list;
                lv_province.setAdapter(new MyAdapter(provinceList));
                //查询市级数据
                AddressDao.getAddressForCode(getActivity(), provinceList.get(0).getRegionCode(), new AddressDao.CallBack() {
                    @Override
                    public void Finish(List<AddressDao.Bean> list) {
                        cityList = list;
                        cityAdapter = new MyAdapter(cityList);
                        lv_city.setAdapter(cityAdapter);
                        //查询县级数据
                        AddressDao.getAddressForCode(getActivity(), cityList.get(0).getRegionCode(), new AddressDao.CallBack() {
                            @Override
                            public void Finish(List<AddressDao.Bean> list) {
                                areaList = list;
                                areaAdapter = new MyAdapter(areaList);
                                lv_area.setAdapter(areaAdapter);
                            }
                        });
                    }
                });
            }
        });
    }

    private void initEvent() {
        //省级列表点击事件
        lv_province.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                allAddress += provinceList.get(position).getDistrictName();
                provinceName = provinceList.get(position).getDistrictName();
                provinceCode = provinceList.get(position).getRegionCode();
                //查询市级数据
                AddressDao.getAddressForCode(getActivity(), provinceList.get(position).getRegionCode(), new AddressDao.CallBack() {
                    @Override
                    public void Finish(List<AddressDao.Bean> list) {
                        cityList.clear();
                        cityList.addAll(list);
                        cityAdapter.notifyDataSetChanged();
                        //查询县级数据
                        AddressDao.getAddressForCode(getActivity(), cityList.get(0).getRegionCode(), new AddressDao.CallBack() {
                            @Override
                            public void Finish(List<AddressDao.Bean> list) {
                                areaList.clear();
                                areaList.addAll(list);
                                areaAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        });
        //市级列表点击事件
        lv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                allAddress += cityList.get(position).getDistrictName();
                cityName = cityList.get(position).getDistrictName();
                cityCode = cityList.get(position).getRegionCode();
                //查询县级数据
                AddressDao.getAddressForCode(getActivity(), cityList.get(position).getRegionCode(), new AddressDao.CallBack() {
                    @Override
                    public void Finish(List<AddressDao.Bean> list) {
                        areaList.clear();
                        areaList.addAll(list);
                        areaAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        //区级列表点击事件
        lv_area.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                allAddress += areaList.get(position).getDistrictName();
                areaName = areaList.get(position).getDistrictName();
                areaCode = areaList.get(position).getRegionCode();
            }
        });
    }

    /**
     * TODO 功能：城市列表适配器
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/10/24
     */
    class MyAdapter extends BaseAdapter {

        private List<AddressDao.Bean> list;

        public MyAdapter(List<AddressDao.Bean> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.city_item, null);
            }
            TextView tv_CityName = convertView.findViewById(R.id.tv_CityName);
            tv_CityName.setText(list.get(position).getDistrictName());
            return convertView;
        }
    }


    /**
     * TODO 功能：获取　省+市+县(区)
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/10/26
     */
    public String getAllAddress() {
        return allAddress;
    }

    /**
     * TODO 功能：获取省级名称
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/10/26
     */
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * TODO 功能：获取省级编码
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/10/26
     */
    public String getProvinceCode() {
        return provinceCode;
    }

    /**
     * TODO 功能：获取市级名称
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/10/26
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * TODO 功能：获取市级编码
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/10/26
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * TODO 功能：获取县(区)级名称
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/10/26
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * TODO 功能：获取县(区)级编码
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/10/26
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * TODO 功能：拷贝数据库到工程中
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/10/24
     */
    private void copyDB(String name) {
        //getCacheDir() : 获取缓存目录
        //getFilesDir() : 获取文件的目录
        File file = new File(getActivity().getFilesDir(), name);
        //判断文件是否存在,存在不去拷贝
        if (!file.exists()) {
            //1.获取assets管理者
            AssetManager assetManager = getActivity().getAssets();
            InputStream in = null;
            FileOutputStream out = null;
            try {
                //2.读取数据库
                in = assetManager.open(name);
                out = new FileOutputStream(file);
                //3.读写操作
                byte[] b = new byte[1024];
                int len = -1;
                while ((len = in.read(b)) != -1) {
                    out.write(b, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //4.关流
                try {
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //xutils中关流操作
                //IOUtils.closeQuietly(in);
                //IOUtils.closeQuietly(out);
            }
        }
    }
}
