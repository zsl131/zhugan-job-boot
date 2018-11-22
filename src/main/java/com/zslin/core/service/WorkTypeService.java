package com.zslin.core.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.dto.QueryListDto;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.tools.JsonTools;
import com.zslin.basic.tools.MyBeanUtils;
import com.zslin.basic.tools.QueryTools;
import com.zslin.core.dao.IWorkTypeDao;
import com.zslin.core.model.WorkType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * 工作岗位，如：服务员、收银员、销售员
 * Created by zsl on 2018/11/20.
 */
@Service
@AdminAuth(name = "工作岗位管理", psn = "业务管理", orderNum = 3, type = "1", url = "/admin/workType")
public class WorkTypeService {

    @Autowired
    private IWorkTypeDao workTypeDao;

    public JsonResult list(String params) {
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<WorkType> res = workTypeDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));

        return JsonResult.getInstance().set("size", (int) res.getTotalElements()).set("data", res.getContent());
    }

    public JsonResult loadOne(String params) {
        try {
            Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
            WorkType obj = workTypeDao.findOne(id);
            return JsonResult.succ(obj);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    public JsonResult addOrUpdate(String params) {
        try {
            WorkType obj = JSONObject.toJavaObject(JSON.parseObject(params), WorkType.class);
            obj.setName(obj.getName().replaceAll(" ", ""));
            if(obj.getId()!=null && obj.getId()>0) {
                WorkType o = workTypeDao.findOne(obj.getId());
                WorkType wt = workTypeDao.findByName(obj.getName());
                if(wt!=null && wt.getId()!=obj.getId()) {return JsonResult.error(obj.getName()+ " 已经存在");}
                MyBeanUtils.copyProperties(obj, o, "id");
                workTypeDao.save(o);
                return JsonResult.succ(o);
            } else {
                if(workTypeDao.findByName(obj.getName())!=null) {return JsonResult.error(obj.getName()+" 已经存在");}
                workTypeDao.save(obj);
                return JsonResult.succ(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    public JsonResult delete(String params) {
        try {
            Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
            workTypeDao.delete(id);
            return JsonResult.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }
}
