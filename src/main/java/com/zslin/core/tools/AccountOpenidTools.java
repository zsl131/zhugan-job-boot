package com.zslin.core.tools;

import com.zslin.basic.tools.JsonTools;
import com.zslin.wx.dao.IAccountDao;
import com.zslin.wx.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zsl on 2018/10/27.
 */
@Component
public class AccountOpenidTools {

    @Autowired
    private IAccountDao accountDao;

    public Account getByParams(String params) {
        try {
            String openid = JsonTools.getJsonParam(params, "openid");
            Account a = accountDao.findByOpenid(openid);
            return a;
        } catch (Exception e) {
            return  null;
        }
    }
}
