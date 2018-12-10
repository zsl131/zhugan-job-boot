package com.zslin.core.service;

import com.zslin.auth.annotation.AuthAnnotation;
import com.zslin.auth.annotation.HasAuthAnnotation;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.tools.JsonTools;
import com.zslin.basic.tools.NormalTools;
import com.zslin.core.dao.IPersonalDao;
import com.zslin.core.dao.IResumeDao;
import com.zslin.core.model.Personal;
import com.zslin.core.model.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 简介管理MINI-C41 - MINI-C50
 * Created by zsl on 2018/12/6.
 */
@Service
@HasAuthAnnotation
public class MiniResumeService {

    @Autowired
    private IResumeDao resumeDao;

    @Autowired
    private IPersonalDao personalDao;

    @AuthAnnotation(name = "小程序获取用户简历", code = "MINI-C41", params = "{openid:''}")
    public JsonResult findByOpenid(String params) {
        String openid = JsonTools.getJsonParam(params, "openid");
        Resume resume = resumeDao.findByOpenid(openid);
        Personal p = personalDao.findByOpenid(openid);
        return JsonResult.success().set("obj", resume).set("personal", p);
    }

    @AuthAnnotation(name = "小程序保存用户简历", code = "MINI-C42", params = "{openid:'', workIds:'', workNames:'', status:'', remark:''}")
    public JsonResult saveResume(String params) {
        String openid = JsonTools.getJsonParam(params, "openid");
        Personal p = personalDao.findByOpenid(openid); //获取用户信息
        String workIds = JsonTools.getJsonParam(params, "workIds");
        String workNames = JsonTools.getJsonParam(params, "workNames");
        String status = JsonTools.getJsonParam(params, "status");
        String remark = JsonTools.getJsonParam(params, "remark");
        Resume r = resumeDao.findByOpenid(openid);
        if(r==null) {
            r = new Resume();
            r.setCreateDate(NormalTools.curDate());
            r.setCreateTime(NormalTools.curDatetime());
            r.setCreateLong(System.currentTimeMillis());
            r.setOpenid(openid);
            r.setHeadimg(p.getHeadimg());
            r.setName(p.getName());
            r.setNickname(p.getNickname());
            r.setIdentity(p.getIdentity());
            r.setPhone(p.getPhone());
            r.setAreaName(p.getAreaName());
            r.setAreaCode(p.getAreaCode());
            r.setTags(p.getTags());
            r.setReadCount(0);
        } else {
            r.setUpdateCount(r.getUpdateCount()+1);
        }
        r.setWorkIds(workIds);
        r.setWorkNames(workNames);
        r.setStatus(status);
        r.setRemark(remark);
        r.setUpdateDate(NormalTools.curDate());
        r.setUpdateTime(NormalTools.curDatetime());
        r.setUpdateLong(System.currentTimeMillis());
        resumeDao.save(r);
        return JsonResult.success("保存成功");
    }

    @AuthAnnotation(name = "小程序设置简历状态", code = "MINI-C43", params = "{openid:'', status:''}")
    public JsonResult updateStatus(String params) {
        try {
            String openid = JsonTools.getJsonParam(params, "openid");
            String status = JsonTools.getJsonParam(params, "status");
            resumeDao.updateStatus(status, openid);
            return JsonResult.success("设置成功");
        } catch (Exception e) {
            return JsonResult.error(e.getMessage());
        }
    }
}
