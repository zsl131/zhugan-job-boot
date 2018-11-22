package com.zslin.wx.utils;

import com.zslin.basic.tools.JsonTools;
import com.zslin.cache.CacheTools;
import com.zslin.wx.model.MiniConfig;
import com.zslin.wx.tools.InternetTools;
import com.zslin.wx.tools.MiniConfigTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 钟述林 393156105@qq.com on 2017/1/24 11:05.
 */
@Component
public class MiniAccessTokenTools {

    private static final String NAME = "mini-access-token";
    private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

//    private static final String TICKET_NAME = "wx-jsapi-ticket";
//    private static final String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=";

    @Autowired
    private CacheTools cacheTools;
    @Autowired
    private MiniConfigTools miniConfigTools;

    public String getAccessToken() {
        String token = (String) cacheTools.getKey(NAME);
        if(token==null || "".equals(token)) {
            token = getNewAccessToken();
            cacheTools.putKey(NAME, token, 7000);
        }
        return token;
    }

    private String getNewAccessToken() {
        MiniConfig config = miniConfigTools.getMiniConfig();
        Map<String, Object> params = new HashMap<>();
        params.put("grant_type", "client_credential");
        params.put("appid", config.getAppid());
        params.put("secret", config.getSecret());
        String result = InternetTools.doGet(TOKEN_URL, params);
        return JsonTools.getJsonParam(result, "access_token");
    }
}
