package com.zslin.core.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zslin.auth.annotation.AuthAnnotation;
import com.zslin.auth.annotation.HasAuthAnnotation;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.tools.JsonTools;
import com.zslin.basic.tools.MyBeanUtils;
import com.zslin.basic.tools.NormalTools;
import com.zslin.core.dao.IPersonalAuthApplyDao;
import com.zslin.core.dao.IPersonalDao;
import com.zslin.core.model.Personal;
import com.zslin.core.model.PersonalAuthApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MINI-C11  -  MINI-C20
 * Created by zsl on 2018/11/14.
 */
@Service
@HasAuthAnnotation
public class MiniPersonalAuthApplyService {

    @Autowired
    private IPersonalAuthApplyDao personalAuthApplyDao;

    @Autowired
    private IPersonalDao personalDao;

    @AuthAnnotation(name = "提交个人认证信息", code = "MINI-C11", params = "{openid:'',nickname:'',headimg:'',name:'',identity:'',sex:'',address:'',frontPic:'',backPic:'',handPic:'',formid:''}")
    public JsonResult add(String params) {
        PersonalAuthApply paa = JSONObject.toJavaObject(JSON.parseObject(params), PersonalAuthApply.class);
        String openid = paa.getOpenid();
        if(openid==null || "".equals(openid.trim())) {return JsonResult.error("未检测到用户信息");}
        PersonalAuthApply p = personalAuthApplyDao.findByOpenid(openid);
        if(p==null) {
            paa.setCreateDate(NormalTools.curDate());
            paa.setCreateTime(NormalTools.curDatetime());
            paa.setCreateLong(System.currentTimeMillis());
            personalAuthApplyDao.save(paa);
            initPersonal(paa); //初始化Personal
            personalDao.updateCheckIdCardByOpenid("2", openid);
            return JsonResult.succ(paa);
        } else {
            MyBeanUtils.copyProperties(paa, p);
            personalAuthApplyDao.save(p);
            personalDao.updateCheckIdCardByOpenid("2", openid);
            return JsonResult.succ(p);
        }
    }

    @AuthAnnotation(name = "获取个人认证申请信息", code = "MINI-C12", params="{openid:''}")
    public JsonResult loadApply(String params) {
        String openid = JsonTools.getJsonParam(params, "openid");
        if(openid==null || "".equals(openid.trim())) {return JsonResult.error("未检测到用户信息");}
        PersonalAuthApply paa = personalAuthApplyDao.findByOpenid(openid);
        return JsonResult.succ(paa);
    }

    //初始化Personal
    private void initPersonal(PersonalAuthApply paa) {
        String openid = paa.getOpenid();
        Personal p = personalDao.findByOpenid(openid);
        if(p==null) {
            p = new Personal();
            p.setCreateLong(System.currentTimeMillis());
            p.setCreateDate(NormalTools.curDate());
            p.setCreateTime(NormalTools.curDatetime());
            p.setHeadimg(paa.getHeadimg());
            p.setNickname(paa.getNickname());
            p.setOpenid(openid);
            personalDao.save(p);
        }
    }
}
