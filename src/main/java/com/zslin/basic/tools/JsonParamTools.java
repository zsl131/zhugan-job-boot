package com.zslin.basic.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by zsl on 2018/8/10.
 */
public class JsonParamTools {

    private static final String HEADER_PARAM_NAME = "headerParams";

    /*private static List<String> ignoreNames = new ArrayList<>();

    static {
        ignoreNames.add("accept-language");
        ignoreNames.add("accept-encoding");
        ignoreNames.add("referer");
        ignoreNames.add("accept");
        ignoreNames.add("auth-token");
        ignoreNames.add("user-agent");
        ignoreNames.add("api-code");
        ignoreNames.add("connection");
        ignoreNames.add("host");
    }*/

    /** 处理来自于React的请求 */
    private static String requestFromReact(HttpServletRequest request) {
        String params = request.getParameter("params");
        try {
//            System.out.println("======="+params);
            params = Base64Utils.getFromBase64(params);
            params = URLDecoder.decode(params, "utf-8");
//            System.out.println("======="+params);
            return rebuildParamsByHeader(params, request);
        } catch (Exception e) {
            e.printStackTrace();
            return params;
        }
    }

    public static String rebuildParams(String from, HttpServletRequest request) {
//        String pwd = request.getParameter("pwd");
//        System.out.println("---->"+pwd);
        if(from!=null && "REACT".equalsIgnoreCase(from)) {
            return requestFromReact(request);
        }
        Enumeration<String> keys = request.getParameterNames();
        JSONObject jsonObj = new JSONObject();
        while(keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = request.getParameter(key);
            value = value.replaceAll("\\\\", "/");
//            jsonObj.put(key, value);
            try {
                jsonObj.put(key, URLDecoder.decode(value, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                jsonObj.put(key, value);
            }
        }

//        return jsonObj.toString();
        return rebuildParamsByHeader(jsonObj.toString(), request);
    }

    public static String rebuildParamsByHeader(String params, HttpServletRequest request) {

        try {
            List<String> ignoreNames = new ArrayList<>();

            ignoreNames.add("accept-language");
            ignoreNames.add("accept-encoding");
            ignoreNames.add("referer");
            ignoreNames.add("accept");
            ignoreNames.add("auth-token");
            ignoreNames.add("user-agent");
            ignoreNames.add("api-code");
            ignoreNames.add("request-from");
            ignoreNames.add("connection");
            ignoreNames.add("host");
            ignoreNames.add("content-type");
            ignoreNames.add("cache-control");
            ignoreNames.add("pragma");

            Enumeration<String> names = request.getHeaderNames();
            Map<String, Object> headerMap = new HashMap<>();
            while(names.hasMoreElements()) {
                String name = names.nextElement();
                if(!ignoreNames.contains(name)) {
                    headerMap.put(name, request.getHeader(name));
                }
            }

            if(params==null || "".equals(params)) {params = "{}";} //
            JSONObject jsonObj = JSON.parseObject(params);
            if(!headerMap.isEmpty()) {
                jsonObj.put(HEADER_PARAM_NAME, headerMap);
            }
            String result = jsonObj.toJSONString();
            return result;
        } catch (Exception e) {
//            e.printStackTrace();
            return params;
        }
    }

    /**
     * 获取Header中的属性值
     * @param params 参数json数据
     * @param fieldName 属性名称
     * @return
     */
    public static String getHeaderField(String params, String fieldName) {
        try {
            JSONObject jsonObj = JSON.parseObject(getHeaderParams(params));
            return jsonObj.getString(fieldName);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getHeaderParams(String params) {
        return JsonTools.getJsonParam(params, HEADER_PARAM_NAME);
    }

    public static Map<String, String> getHeaderMap(String params) {
        Map<String, String> result = new HashMap<>();
        try {
            JSONObject jsonObj = JSON.parseObject(getHeaderParams(params));
            Iterator<String> keys = jsonObj.keySet().iterator();
            while(keys.hasNext()) {
                String key = keys.next();
                result.put(key, jsonObj.getString(key));
            }
        } catch (Exception e) {
        }
        return result;
    }
}
