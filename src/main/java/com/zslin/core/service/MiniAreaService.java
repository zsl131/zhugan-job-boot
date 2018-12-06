package com.zslin.core.service;

import com.zslin.auth.annotation.AuthAnnotation;
import com.zslin.auth.annotation.HasAuthAnnotation;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.tools.JsonTools;
import com.zslin.core.dao.IAreaDao;
import com.zslin.core.dao.IPersonalDao;
import com.zslin.core.dao.IResumeDao;
import com.zslin.core.model.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 区域管理 MINI-C31  -  MINI-C40
 * Created by zsl on 2018/12/6.
 */
@Service
@HasAuthAnnotation
public class MiniAreaService {

    @Autowired
    private IAreaDao areaDao;

    @Autowired
    private IPersonalDao personalDao;

    @Autowired
    private IResumeDao resumeDao;

    @AuthAnnotation(name = "获取已开通的区域", code = "MINI-C31")
    public JsonResult listOpeningArea(String params) {
        List<Area> list = areaDao.findByStatus("1");
        return JsonResult.success("获取成功").set("data", list).set("size", list.size());
    }

    @AuthAnnotation(name = "设置用户区域", code = "MINI-C32", params = "{openid:'', code: '', name: ''}")
    public JsonResult modifyArea(String params) {
        try {
            String openid = JsonTools.getJsonParam(params, "openid");
            String code = JsonTools.getJsonParam(params, "code");
            String name = JsonTools.getJsonParam(params, "name");
            personalDao.updateArea(code, name, openid); //修改区域
            resumeDao.updateArea(code, name, openid); //修改区域
            return JsonResult.success("设置成功");
        } catch (Exception e) {
            return JsonResult.error(e.getMessage());
        }
    }
}
