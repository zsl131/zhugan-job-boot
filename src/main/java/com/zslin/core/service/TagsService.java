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
import com.zslin.core.dao.ITagsDao;
import com.zslin.core.model.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * 用于求职人员为自己设置标签，如：勤劳好学、吃苦耐劳...
 * Created by zsl on 2018/11/20.
 */
@Service
@AdminAuth(name = "标签管理", psn = "业务管理", orderNum = 3, type = "1", url = "/admin/tags")
public class TagsService {

    @Autowired
    private ITagsDao tagsDao;

    public JsonResult list(String params) {
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<Tags> res = tagsDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));

        return JsonResult.getInstance().set("size", (int) res.getTotalElements()).set("data", res.getContent());
    }

    public JsonResult loadOne(String params) {
        try {
            Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
            Tags obj = tagsDao.findOne(id);
            return JsonResult.succ(obj);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    public JsonResult addOrUpdate(String params) {
        try {
            Tags obj = JSONObject.toJavaObject(JSON.parseObject(params), Tags.class);
            obj.setText(obj.getText().replaceAll(",", "，").replaceAll(" ", ""));
            if(obj.getId()!=null && obj.getId()>0) {
                Tags t = tagsDao.findByText(obj.getText());
                if(t!=null && t.getId()!=obj.getId()) {return JsonResult.error(obj.getText()+ " 已经存在");}
                Tags o = tagsDao.findOne(obj.getId());
                MyBeanUtils.copyProperties(obj, o, "id", "count");
                tagsDao.save(o);
                return JsonResult.succ(o);
            } else {
                if(tagsDao.findByText(obj.getText())!=null) {return JsonResult.error(obj.getText()+" 已经存在");}
                tagsDao.save(obj);
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
            tagsDao.delete(id);
            return JsonResult.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }
}
