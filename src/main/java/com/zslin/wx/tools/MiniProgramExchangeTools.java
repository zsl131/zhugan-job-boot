package com.zslin.wx.tools;

import com.alibaba.fastjson.JSONObject;
import com.qq.weixin.mp.aes.MiniProgramTools;
import com.zslin.basic.tools.ConfigTools;
import com.zslin.basic.tools.JsonTools;
import com.zslin.basic.tools.NormalTools;
import com.zslin.wx.dao.IAccountDao;
import com.zslin.wx.model.Account;
import com.zslin.wx.model.MiniConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

/**
 * 小程序数据交互工具类
 * Created by zsl on 2018/10/21.
 */
@Component
public class MiniProgramExchangeTools {

    @Autowired
    private IAccountDao accountDao;

    @Autowired
    private MiniConfigTools miniConfigTools;

    @Autowired
    private ExchangeTools exchangeTools;

    @Autowired
    private ConfigTools configTools;

    public Account miniLogin(String code, String iv, String pwd) {
//        System.out.println("code:"+code);
//        System.out.println("iv:"+iv);
//        System.out.println("pwd:"+pwd);
        MiniConfig miniConfig = miniConfigTools.getMiniConfig();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+miniConfig.getAppid()+"&secret="+miniConfig.getSecret()+"&js_code="+code+"&grant_type=authorization_code";
        String result = InternetTools.doGet(url, null);
        String sessionKey = JsonTools.getJsonParam(result, "session_key");
        System.out.println("sessionKey:"+sessionKey);
        JSONObject jsonObj = MiniProgramTools.getUserInfo(pwd, sessionKey, iv);
        String openid = jsonObj.getString("openId");
        Account a = accountDao.findByOpenid(openid);
        if(a==null) {
            a = buildAccount(openid, jsonObj);
        }
        return a;
    }

    private Account buildAccount(String openid, JSONObject jsonObj) {
        Account a = new Account();
        String nickname = "";
        try {
            nickname = jsonObj.getString("nickName");
            nickname = nickname.replaceAll("[^\\u0000-\\uFFFF]", ""); //替换utf8mb4字符
        } catch (Exception e) {
        }
        String province = jsonObj.getString("province");
        String city = jsonObj.getString("city");
        String avatarUrl = jsonObj.getString("avatarUrl");
        String headimg = exchangeTools.saveHeadImg(configTools.getUploadPath("headimg/",true)+ UUID.randomUUID().toString()+".jpg",avatarUrl).replace(configTools.getUploadPath(false), File.separator);
        Integer gender = jsonObj.getInteger("gender");
        String sex = gender==1?"1":"2";
        a.setCity(city);
        a.setHasBindPhone("0");
        a.setHeadimg(headimg.replaceAll("\\\\", "/"));
        a.setNickname(nickname);
        a.setOpenid(openid);
        a.setProvince(province);
        a.setSex(sex);

        a.setCreateDate(NormalTools.curDate("yyyy-MM-dd"));
        a.setCreateTime(NormalTools.curDatetime());
        a.setCreateLong(System.currentTimeMillis()/1000);
//        accountDao.save(a);
        return a;
    }
}
