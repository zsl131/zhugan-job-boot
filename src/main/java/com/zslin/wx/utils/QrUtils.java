package com.zslin.wx.utils;

import com.zslin.basic.tools.ConfigTools;
import com.zslin.wx.tools.InternetTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 二维码生成工具
 * Created by zsl on 2018/10/27.
 */
@Component
public class QrUtils {

    @Autowired
    private MiniAccessTokenTools miniAccessTokenTools;

    @Autowired
    private ConfigTools configTools;

    public void generateQr(String scene) {
        String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+miniAccessTokenTools.getAccessToken();
        System.out.println(url);
        Map<String, Object> params = new HashMap<>();
        params.put("scene", scene);
        String result = InternetTools.sendPost(url, params);
        System.out.println(result);
    }

    public void getminiqrQr(String sceneStr) {
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+miniAccessTokenTools.getAccessToken();
            Map<String,Object> param = new HashMap<>();
            param.put("scene", sceneStr);
//            param.put("page", "pages/index/index");
//            param.put("width", 430);
//            param.put("auto_color", false);
            Map<String,Object> line_color = new HashMap<>();
            line_color.put("r", 231);
            line_color.put("g", 74);
            line_color.put("b", 69);
            param.put("line_color", line_color);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(param, headers);
            ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
            byte[] result = entity.getBody();
            inputStream = new ByteArrayInputStream(result);

            File file = new File(configTools.getUploadPath("mini_qr", true) + UUID.randomUUID()+".png");
            if (!file.exists()){
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
