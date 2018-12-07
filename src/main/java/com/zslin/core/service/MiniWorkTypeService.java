package com.zslin.core.service;

import com.zslin.auth.annotation.AuthAnnotation;
import com.zslin.auth.annotation.HasAuthAnnotation;
import com.zslin.basic.dto.JsonResult;
import com.zslin.core.dao.IWorkTypeDao;
import com.zslin.core.model.WorkType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MINI-WT01
 * Created by zsl on 2018/12/6.
 */
@Service
@HasAuthAnnotation
public class MiniWorkTypeService {

    @Autowired
    private IWorkTypeDao workTypeDao;

    @AuthAnnotation(name = "小程序获取所有岗位", code = "MINI-WT01")
    public JsonResult listAllTypes(String params) {
        List<WorkType> list = workTypeDao.findAll();
        return JsonResult.success("获取成功").set("data", list).set("size", list.size());
    }
}
