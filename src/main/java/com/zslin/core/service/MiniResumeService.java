package com.zslin.core.service;

import com.zslin.auth.annotation.AuthAnnotation;
import com.zslin.auth.annotation.HasAuthAnnotation;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.dto.QueryListDto;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.tools.JsonTools;
import com.zslin.basic.tools.NormalTools;
import com.zslin.basic.tools.QueryTools;
import com.zslin.core.dao.IPersonalDao;
import com.zslin.core.dao.IResumeDao;
import com.zslin.core.dao.IResumeStoreDao;
import com.zslin.core.model.Personal;
import com.zslin.core.model.Resume;
import com.zslin.core.model.ResumeStore;
import com.zslin.core.tools.ResumeStoreTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * 简历管理MINI-C41 - MINI-C50
 * Created by zsl on 2018/12/6.
 */
@Service
@HasAuthAnnotation
public class MiniResumeService {

    @Autowired
    private IResumeDao resumeDao;

    @Autowired
    private IPersonalDao personalDao;

    @Autowired
    private IResumeStoreDao resumeStoreDao;

    @Autowired
    private ResumeStoreTools resumeStoreTools;

    @AuthAnnotation(name = "小程序取消收藏简历", code = "MINI-C49", params = "{openid:'', id:0}")
    public JsonResult removeStore(String params) {
        String openid = JsonTools.getParamOpenid(params);
        Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
        resumeStoreDao.deleteByOpenidAndResumeId(openid, id);
        return JsonResult.success("取消收藏成功");
    }

    @AuthAnnotation(name = "小程序获取收藏信息", code = "MINI-C48", params = "{openid: '', page:0}")
    public JsonResult listStore(String params) {
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<ResumeStore> res = resumeStoreDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));

        return JsonResult.getInstance().set("size", res.getTotalElements()).set("data", resumeStoreTools.buildResumeStore(res.getContent()));
    }

    @AuthAnnotation(name = "小程序收藏简历", code = "MINI-C47", params = "{openid:'', id: 0}")
    public JsonResult onStore(String params) {
        Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
        String openid = JsonTools.getJsonParam(params, "openid");
        ResumeStore rs = resumeStoreDao.findByOpenidAndResumeId(openid, id);
        if(rs==null) {
            rs = new ResumeStore();
            rs.setCreateTime(NormalTools.curDatetime());
            rs.setCreateLong(System.currentTimeMillis());
            rs.setCreateDate(NormalTools.curDate());
            rs.setOpenid(openid);
            rs.setResumeId(id);
            resumeStoreDao.save(rs);
            resumeDao.updateStore(1, id); //收藏加1
            return JsonResult.success("操作成功").set("store", rs);
        } else {
            resumeDao.updateStore(-1, id);
            resumeStoreDao.deleteByOpenidAndResumeId(openid, id);
            return JsonResult.success("操作成功");
        }

    }

    @AuthAnnotation(name = "小程序记录显示简历", code = "MINI-C46", params = "{openid: '', id: 0}")
    public JsonResult onShow(String params) {
        Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
        resumeDao.updateReadCount(id);
        return JsonResult.success();
    }

    @AuthAnnotation(name = "小程序列表简历", code = "MINI-C44", params = "")
    public JsonResult list(String params) {
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<Resume> res = resumeDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));

        return JsonResult.getInstance().set("size", res.getTotalElements()).set("data", res.getContent());
    }

    @AuthAnnotation(name = "小程序通过ID获取用户简历", code = "MINI-C45", params = "{id:0, openid:''}")
    public JsonResult findById(String params) {
        try {
            Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
            String openid = JsonTools.getJsonParam(params, "openid");
            Resume r = resumeDao.findOne(id);
            Personal p = personalDao.findByOpenid(r.getOpenid());
            ResumeStore rs = resumeStoreDao.findByOpenidAndResumeId(openid, id);
            return JsonResult.succ(r).set("personal", p).set("store", rs);
        } catch (Exception e) {
            return JsonResult.error(e.getMessage());
        }
    }

    @AuthAnnotation(name = "小程序通过Openid获取用户简历", code = "MINI-C41", params = "{openid:''}")
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
            r.setSex(p.getSex());
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
