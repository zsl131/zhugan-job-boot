package com.zslin.core.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zslin.auth.annotation.AuthAnnotation;
import com.zslin.auth.annotation.HasAuthAnnotation;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.tools.JsonTools;
import com.zslin.basic.tools.NormalTools;
import com.zslin.core.dao.IPersonalDao;
import com.zslin.core.model.Personal;
import com.zslin.wx.dao.IAccountDao;
import com.zslin.wx.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 小程序使用 MINI-C01  -  MINI-C10
 * Created by zsl on 2018/11/14.
 */
@Service
@HasAuthAnnotation
public class MiniPersonalService {

    @Autowired
    private IPersonalDao personalDao;

    @Autowired
    private IAccountDao accountDao;

    @AuthAnnotation(name = "小程序设置工作状态", code = "MINI-C04", params = "{openid:'', status:''}")
    public JsonResult modifyWorkStatus(String params) {
        String openid = JsonTools.getJsonParam(params, "openid");
        String status = JsonTools.getJsonParam(params, "status");
        personalDao.updateWorkStatus(status, openid);
        return JsonResult.success("设置成功");
    }

    @AuthAnnotation(name = "小程序绑定手机号码", code = "MINI-C03", params = "{openid:'', phone:''}")
    public JsonResult bindPhone(String params) {
        String phone = JsonTools.getJsonParam(params, "phone");
        String openid = JsonTools.getJsonParam(params, "openid");
        personalDao.updatePhone(phone, openid);
        return JsonResult.success("绑定成功");
    }

    @AuthAnnotation(name="获取用户基本信息", code="MINI-C02", params="{openid:''}")
    public JsonResult loadAccount(String params) {
        String openid = JsonTools.getJsonParam(params, "openid");
        Account account = accountDao.findByOpenid(openid);
        Personal personal = personalDao.findByOpenid(openid);
        return JsonResult.success("获取成功").set("account", account).set("personal", personal);
    }

    @AuthAnnotation(name = "初始个人信息", code = "MINI-C01", params = "{openid:'',nickname:'',headimg:'',type:'', tags:''}")
    public JsonResult addOrUpdate(String params) {
        Personal p = JSONObject.toJavaObject(JSON.parseObject(params), Personal.class);
        if(p.getOpenid()==null || "".equals(p.getOpenid())) {
            return JsonResult.error("未获取用户信息");
        }
        Personal per = personalDao.findByOpenid(p.getOpenid());
        if(per==null) {
            p.setCreateDate(NormalTools.curDate());
            p.setCreateTime(NormalTools.curDatetime());
            p.setCreateLong(System.currentTimeMillis());
            personalDao.save(p);
            return JsonResult.succ(p);
        } else {
            per.setAddress(p.getAddress());
            per.setRemark(p.getRemark());
            per.setTags(p.getTags());
            per.setType(p.getType());
            per.setWorkStatus(p.getWorkStatus());
            personalDao.save(per);
            return JsonResult.succ(per);
        }
    }
}
