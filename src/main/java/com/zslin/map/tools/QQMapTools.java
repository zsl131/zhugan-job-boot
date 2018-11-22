package com.zslin.map.tools;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 腾讯地图工具类
 * Created by zsl on 2018/10/22.
 */
@Component
public class QQMapTools {

    public static final String KEY = "XXQBZ-SQZKF-SKLJT-NJGNK-AUV5J-KMBUD"; //腾讯地图KEY，后期修改到数据库

    private String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }

    public void queryStations(String name, double latitude, double longitude) {
//        String url = "https://apis.map.qq.com/ws/place/v1/search?keyword="+encode(name)+"&boundary=region("+city+",0)&key="+KEY;
        String url = "https://apis.map.qq.com/ws/place/v1/search?filter=category=公交站&keyword="+encode(name)+"&boundary=nearby("+latitude+","+longitude+",1000,0)&key="+KEY;
        System.out.println(url);
        String result = MapRequestTools.doGet(url, null);
        System.out.println("======================");
        System.out.println(result);
    }
}
