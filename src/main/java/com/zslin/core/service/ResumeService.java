package com.zslin.core.service;

import com.zslin.auth.annotation.HasAuthAnnotation;
import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.dto.QueryListDto;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.tools.JsonTools;
import com.zslin.basic.tools.QueryTools;
import com.zslin.core.dao.IResumeDao;
import com.zslin.core.model.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * Created by zsl on 2018/12/10.
 */
@Service
@HasAuthAnnotation
@AdminAuth(name = "简历管理", psn = "业务管理", orderNum = 3, type = "1", url = "/admin/resume")
public class ResumeService {

    @Autowired
    private IResumeDao resumeDao;

    /** 列表 */
    public JsonResult list(String params) {
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<Resume> res = resumeDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));

        return JsonResult.getInstance().set("size", res.getTotalElements()).set("data", res.getContent());
    }

    public JsonResult modifyField(String params) {
        try {
            String field = JsonTools.getJsonParam(params, "field");
            String value = JsonTools.getJsonParam(params, "value");
            Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
            String hql = "UPDATE Resume r SET r."+field+"=?1 WHERE p.id=?2";
            resumeDao.updateByHql(hql, value, id);
            return JsonResult.success("设置成功");
        } catch (Exception e) {
            return JsonResult.error(e.getMessage());
        }
    }

    /** 审核简历 */
    public JsonResult verify(String params) {
        try {
            System.out.println("==="+params);
            String status = JsonTools.getJsonParam(params, "status");
            Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
            String reason = JsonTools.getJsonParam(params, "reason");
            resumeDao.updateVerify(status, reason, id);
            return JsonResult.success("操作成功");
        } catch (Exception e) {
            return JsonResult.error(e.getMessage());
        }
    }
}
