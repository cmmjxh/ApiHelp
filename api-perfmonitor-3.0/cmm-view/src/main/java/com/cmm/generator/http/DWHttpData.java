package com.cmm.generator.http;

import com.alibaba.fastjson.JSONObject;
import com.cmm.utils.ConfigUtils;
import com.cmm.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * TODO 获取相关元数据
 *
 * @version 1.0
 * @author: Survivor
 * @createTime: 2022/6/30$ 9:29$
 */
public class DWHttpData {
    final static String API_URL = ConfigUtils.getConfig("ApiUrl");

    /**
     * 获取指定API元数据
     */
    public static Map getApiMetadata(String apiName) throws Exception {
        Map data = new HashMap();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api_name", apiName);
        try {
            Map responseData = HttpUtils.post(jsonObject, API_URL);
            if (responseData != null) {
                Map execution = (Map) responseData.get("execution");
                if (execution != null) {
                    String code = (String) execution.get("code");
                    if ("000".equals(code)) {
                        data = (Map) responseData.get("data");
                        data.put("code", "0");
                    } else if ("201".equals(code)) {
                        data.put("code", "201");
                    }
                }
            }
        }catch (Exception E){
            data.put("code", "500");
        }
        return data;
    }
}