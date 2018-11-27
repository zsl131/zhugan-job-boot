package com.zslin.core.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zslin.auth.annotation.AuthAnnotation;
import com.zslin.auth.annotation.HasAuthAnnotation;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.tools.JsonTools;
import com.zslin.basic.tools.MyBeanUtils;
import com.zslin.basic.tools.NormalTools;
import com.zslin.core.dao.ICompanyAuthApplyDao;
import com.zslin.core.dao.IPersonalDao;
import com.zslin.core.model.CompanyAuthApply;
import com.zslin.core.model.Personal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MINI-C21  -  MINI-C30
 * Created by zsl on 2018/11/27.
 */
@Service
@HasAuthAnnotation
public class MiniCompanyAuthApplyService {

    @Autowired
    private ICompanyAuthApplyDao companyAuthApplyDao;

    @Autowired
    private IPersonalDao personalDao;

    @AuthAnnotation(name = "提交单位认证信息", code = "MINI-C21", params = "{openid:'',nickname:'',headimg:'',companyName:'',bossName:'',companyNo:'',companyAddress:'',frontPic:'',licensePic:'',formid:''}")
    public JsonResult add(String params) {
        CompanyAuthApply paa = JSONObject.toJavaObject(JSON.parseObject(params), CompanyAuthApply.class);
        String openid = paa.getOpenid();
        if(openid==null || "".equals(openid.trim())) {return JsonResult.error("未检测到用户信息");}
        CompanyAuthApply p = companyAuthApplyDao.findByOpenid(openid);
        if(p==null) {

            Personal personal = personalDao.findByOpenid(openid);

            paa.setCreateDate(NormalTools.curDate());
            paa.setCreateTime(NormalTools.curDatetime());
            paa.setCreateLong(System.currentTimeMillis());
            paa.setPersonalIdentity(personal.getIdentity());
            paa.setPersonalName(personal.getName());
            paa.setPersonalPhone(personal.getPhone());
            companyAuthApplyDao.save(paa);
            personalDao.updateCheckCompanyByOpenid("2", openid);
            return JsonResult.succ(paa);
        } else {
            MyBeanUtils.copyProperties(paa, p);
            companyAuthApplyDao.save(p);
            personalDao.updateCheckCompanyByOpenid("2", openid);
            return JsonResult.succ(p);
        }
    }

    @AuthAnnotation(name = "获取单位认证申请信息", code = "MINI-C22", params="{openid:''}")
    public JsonResult loadApply(String params) {
        String openid = JsonTools.getJsonParam(params, "openid");
        if(openid==null || "".equals(openid.trim())) {return JsonResult.error("未检测到用户信息");}
        CompanyAuthApply paa = companyAuthApplyDao.findByOpenid(openid);
        return JsonResult.succ(paa);
    }
}
