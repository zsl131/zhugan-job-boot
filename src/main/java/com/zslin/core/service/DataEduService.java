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
import com.zslin.core.dao.IDataEduDao;
import com.zslin.core.dao.IPersonalDao;
import com.zslin.core.dao.IResumeDao;
import com.zslin.core.model.DataEdu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 *
 * Created by zsl on 2018/12/8.
 */
@Service
@AdminAuth(name = "学历数据配置", psn = "业务管理", orderNum = 3, type = "1", url = "/admin/dataEdu")
public class DataEduService {

    @Autowired
    private IDataEduDao dataEduDao;

    @Autowired
    private IPersonalDao personalDao;

    @Autowired
    private IResumeDao resumeDao;

    public JsonResult list(String params) {
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<DataEdu> res = dataEduDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));

        return JsonResult.getInstance().set("size", (int) res.getTotalElements()).set("data", res.getContent());
    }

    public JsonResult loadOne(String params) {
        try {
            Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
            DataEdu obj = dataEduDao.findOne(id);
            return JsonResult.succ(obj);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    public JsonResult addOrUpdate(String params) {
        try {
            DataEdu obj = JSONObject.toJavaObject(JSON.parseObject(params), DataEdu.class);
            obj.setName(obj.getName().replaceAll(" ", ""));
            if(obj.getId()!=null && obj.getId()>0) {
                DataEdu t = dataEduDao.findByName(obj.getName());
                if(t!=null && t.getId()!=obj.getId()) {return JsonResult.error(obj.getName()+ " 已经存在");}
                DataEdu o = dataEduDao.findOne(obj.getId());
                MyBeanUtils.copyProperties(obj, o);
                dataEduDao.save(o);
                return JsonResult.succ(o);
            } else {
                if(dataEduDao.findByName(obj.getName())!=null) {return JsonResult.error(obj.getName()+" 已经存在");}
                dataEduDao.save(obj);
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
            dataEduDao.delete(id);
            return JsonResult.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }
}
