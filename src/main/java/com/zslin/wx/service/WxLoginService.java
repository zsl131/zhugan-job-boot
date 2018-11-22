package com.zslin.wx.service;

import com.zslin.auth.annotation.AuthAnnotation;
import com.zslin.auth.annotation.HasAuthAnnotation;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.tools.JsonTools;
import com.zslin.wx.dao.IAccountDao;
import com.zslin.wx.model.Account;
import com.zslin.wx.tools.MiniProgramExchangeTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zsl on 2018/10/19.
 */
@Service
@HasAuthAnnotation
public class WxLoginService {

    @Autowired
    private MiniProgramExchangeTools miniProgramExchangeTools;

    @Autowired
    private IAccountDao accountDao;

    @AuthAnnotation(name = "微信授权登陆", code = "WX_LOGIN", params = "{code:'',pwd:'', iv:'', brand:'手机品牌',model:'手机型号'}")
    public JsonResult login(String params) {
        try {
            String code = JsonTools.getJsonParam(params, "code");
            String pwd = JsonTools.getJsonParam(params, "pwd");
            pwd = pwd.replaceAll(" ", "+").replaceAll("\\r|\\n", "");
            String iv = JsonTools.getJsonParam(params, "iv");
            iv = iv.replaceAll(" ", "+");
            String brand = JsonTools.getJsonParam(params, "brand");
            String model = JsonTools.getJsonParam(params, "model");
            Account a = miniProgramExchangeTools.miniLogin(code, iv, pwd);
            a.setBrand(brand);
            a.setModel(model);

            accountDao.save(a);
            return JsonResult.success("访问成功").set("account", a);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error("授权失败，请重试");
        }
    }
}
