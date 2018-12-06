package com.zslin.core.service;

import com.zslin.auth.annotation.AuthAnnotation;
import com.zslin.auth.annotation.HasAuthAnnotation;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.tools.JsonTools;
import com.zslin.core.dao.IPersonalDao;
import com.zslin.core.dao.IResumeDao;
import com.zslin.core.dao.ITagsDao;
import com.zslin.core.model.Personal;
import com.zslin.core.model.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标签设置
 * MINI-CT
 * Created by zsl on 2018/11/28.
 */
@Service
@HasAuthAnnotation
public class MiniTagsService {

    @Autowired
    private ITagsDao tagsDao;

    @Autowired
    private IPersonalDao personalDao;

    @Autowired
    private IResumeDao resumeDao;

    @AuthAnnotation(name = "小程序标签列表", code = "MINI-CT01", params = "{openid:''}")
    public JsonResult list(String params) {
        String openid = JsonTools.getJsonParam(params, "openid");
        Personal p = personalDao.findByOpenid(openid);
        if(p==null) {return JsonResult.error("未检测数据");}
        List<Tags> list = tagsDao.findAll();
        return JsonResult.success("获取成功").set("data", list).set("size", list.size()).set("personal", p);
    }

    @AuthAnnotation(name = "小程序设置标签", code = "MINI-CT02", params = "{openid:'',tags:'', tagsIds:''}")
    public JsonResult setTags(String params) {
        try {
            String openid = JsonTools.getJsonParam(params, "openid");
            String tags = JsonTools.getJsonParam(params, "tags");
            String tagsIds = JsonTools.getJsonParam(params, "tagsIds");
            String [] array = tagsIds.split(",");
            for(String idStr : array) {
                if(idStr==null || "".equals(idStr.trim())) {continue;}
                Integer id = Integer.parseInt(idStr);
                tagsDao.plusCount(id);
            }
            if(tags.endsWith(",")) {tags = tags.substring(0, tags.length()-1);}
            if(tagsIds.endsWith(",")) {tagsIds = tagsIds.substring(0, tagsIds.length()-1);}
            personalDao.updateTags(tags, tagsIds, openid);
            resumeDao.updateTags(tags, openid); //修改简历中的标签
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }

        return JsonResult.success("操作成功");
    }
}
