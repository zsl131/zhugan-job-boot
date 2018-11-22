package com.zslin.core.service;

import com.zslin.auth.annotation.AuthAnnotation;
import com.zslin.auth.annotation.HasAuthAnnotation;
import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.dto.QueryListDto;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.tools.JsonTools;
import com.zslin.basic.tools.QueryTools;
import com.zslin.core.dao.IPersonalDao;
import com.zslin.core.model.Personal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * Created by zsl on 2018/11/14.
 */
@Service
@HasAuthAnnotation
@AdminAuth(name = "用户管理", psn = "业务管理", orderNum = 3, type = "1", url = "/admin/personal")
public class PersonalService {

    @Autowired
    private IPersonalDao personalDao;

    /** 列表 */
    public JsonResult list(String params) {
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<Personal> res = personalDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));

        return JsonResult.getInstance().set("size", res.getTotalElements()).set("data", res.getContent());
    }

    @AuthAnnotation(name = "修改个人属性值", code="C02", params = "{openid:'', field: '', value: ''}")
    public JsonResult modifyField(String params) {
        try {
            String field = JsonTools.getJsonParam(params, "field");
            String value = JsonTools.getJsonParam(params, "value");
            String openid = JsonTools.getJsonParam(params, "openid");
            String hql = "UPDATE Personal p SET p."+field+"=?1 WHERE p.openid=?2";
            personalDao.updateByHql(hql, value, openid);
            return JsonResult.success("设置成功");
        } catch (Exception e) {
            return JsonResult.error(e.getMessage());
        }
    }
}
