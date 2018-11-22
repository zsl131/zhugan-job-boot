package com.zslin.basic.service;

import com.zslin.basic.dao.IAppConfigDao;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.dto.WebBaseDto;
import com.zslin.basic.model.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * webInterceptorService.loadWebBase
 * Created by zsl on 2018/7/24.
 */
@Service
public class WebInterceptorService {

    @Autowired
    private IAppConfigDao appConfigDao;

//    @Autowired
//    private IWxConfigDao wxConfigDao;

    public JsonResult loadWebBase(String params) {
//        WxConfig wc = wxConfigDao.loadOne();
//        if(wc==null) {wc = new WxConfig();}

        AppConfig ac = appConfigDao.loadOne();

        WebBaseDto wbd = new WebBaseDto();
        wbd.setAc(ac);
//        wbd.setWc(wc);

        return JsonResult.getInstance().set("appConfig", ac);
    }
}
