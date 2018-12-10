package com.zslin.core.service;

import com.zslin.auth.annotation.AuthAnnotation;
import com.zslin.auth.annotation.HasAuthAnnotation;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.tools.JsonTools;
import com.zslin.core.dao.IDataEduDao;
import com.zslin.core.dao.IPersonalDao;
import com.zslin.core.dao.IResumeDao;
import com.zslin.core.model.DataEdu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学历管理MINI-DE
 * Created by zsl on 2018/12/8.
 */
@Service
@HasAuthAnnotation
public class MiniDataEduService {

    @Autowired
    private IDataEduDao dataEduDao;

    @Autowired
    private IPersonalDao personalDao;

    @Autowired
    private IResumeDao resumeDao;

    @AuthAnnotation(name = "小程序获取学历", code = "MINI-DE01")
    public JsonResult listAll(String params) {
        List<DataEdu> list = dataEduDao.findAll();
        return JsonResult.success().set("data", list).set("size", list.size());
    }

    @AuthAnnotation(name = "小程序设置用户学历", code = "MINI-DE02", params = "{openid:'', eduId:0, eduName:''}")
    public JsonResult updatePersonalEdu(String params) {
        try {
            String openid = JsonTools.getJsonParam(params, "openid");
            Integer eduId = Integer.parseInt(JsonTools.getJsonParam(params, "eduId"));
            String eduName = JsonTools.getJsonParam(params, "eduName");
            personalDao.updateEdu(eduId, eduName, openid);
            resumeDao.updateEdu(eduId, eduName, openid);
            return JsonResult.success();
        } catch (Exception e) {
            return JsonResult.error(e.getMessage());
        }
    }
}
