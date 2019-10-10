package com.shenl.map.CallBack;

import java.util.Map;

public interface LocationListener {
    void success(Map<String, String> map);
    void error(String error);
}
