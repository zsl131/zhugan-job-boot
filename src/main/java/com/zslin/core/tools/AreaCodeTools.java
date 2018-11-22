package com.zslin.core.tools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zslin.basic.tools.JsonTools;
import com.zslin.core.dto.AreaDto;
import com.zslin.map.tools.MapRequestTools;

/**
 * 行政区划处理工具类
 * Created by zsl on 2018/10/24.
 */
public class AreaCodeTools {

    public static AreaDto getAreaByParams(String params) {
        try {
            Double lat = Double.parseDouble(JsonTools.getJsonParam(params, "lat"));
            Double lng = Double.parseDouble(JsonTools.getJsonParam(params, "lng"));
            AreaDto dto = getAreaCode(lat, lng);
            return dto;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 通过经纬度获取行政区划
     * @param lat 经度
     * @param lng 纬度
     * @return
     */
    public static AreaDto getAreaCode(Double lat, Double lng) {
        try {
            String url = "http://gc.ditu.aliyun.com/regeocoding?l="+lat+","+lng+"&type=100";
            String resStr = MapRequestTools.doGet(url, null);
            //System.out.println("resStr::"+resStr);
            JSONObject jsonObj = JSONObject.parseObject(resStr);
            JSONArray jsonArray = jsonObj.getJSONArray("addrList");
            JSONObject targetObj = jsonArray.getJSONObject(0);

            String code = targetObj.getString("admCode");
            String name = targetObj.getString("name");
            String admName = targetObj.getString("admName");

            String pCode = code.substring(0,2)+"0000";
            String cCode = code.substring(0,4)+"00";
            String [] nameArray = admName.split(",");

            AreaDto dto = new AreaDto();
            dto.setProvince(nameArray[0]);
            dto.setCity(nameArray.length>=3?nameArray[1]:nameArray[0]);
            dto.setCityCode(cCode);
            dto.setCounty(nameArray.length>=3?nameArray[2]:nameArray[1]);
            dto.setCountyCode(code);
            dto.setProvinceCode(pCode);
            dto.setStreet(name);
            dto.setLat(lat);
            dto.setLng(lng);

            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
