package com.zslin.core.service;

import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.dto.QueryListDto;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.tools.JsonParamTools;
import com.zslin.basic.tools.JsonTools;
import com.zslin.basic.tools.NormalTools;
import com.zslin.basic.tools.QueryTools;
import com.zslin.core.dao.IPersonalAuthApplyDao;
import com.zslin.core.dao.IPersonalDao;
import com.zslin.core.model.Personal;
import com.zslin.core.model.PersonalAuthApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * Created by zsl on 2018/11/19.
 */
@Service
@AdminAuth(name = "个人身份认证管理", psn = "业务管理", orderNum = 3, type = "1", url = "/admin/personalAuthApply")
public class PersonalAuthApplyService {

    @Autowired
    private IPersonalAuthApplyDao personalAuthApplyDao;

    @Autowired
    private IPersonalDao personalDao;

    /** 列表 */
    public JsonResult list(String params) {
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<PersonalAuthApply> res = personalAuthApplyDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));

        return JsonResult.getInstance().set("size", res.getTotalElements()).set("data", res.getContent());
    }

    /** 审核 */
    public JsonResult verify(String params) {
        String status = JsonTools.getJsonParam(params, "status");
        Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
        String reason = JsonTools.getJsonParam(params, "reason");
        String username = JsonParamTools.getHeaderField(params, "username"); //登陆用户的用户名
        PersonalAuthApply paa = personalAuthApplyDao.findOne(id);
        paa.setReason(reason);
        paa.setStatus(status);
        paa.setVerifyDate(NormalTools.curDate());
        paa.setVerifyTime(NormalTools.curDatetime());
        paa.setVerifyLong(System.currentTimeMillis());
        paa.setVerifyUser(username);
        if("1".equals(status)) { //如果是通过，修改Personal属性
//            personalDao.updateCheckIdCardByOpenid("1", paa.getOpenid());
            Personal p = personalDao.findByOpenid(paa.getOpenid());
            p.setCheckIdcard("1");
            p.setAddress(paa.getAddress());
            p.setIdentity(paa.getIdentity());
            p.setName(paa.getName());
            p.setSex(paa.getSex());
            personalDao.save(p);
        } else if("2".equals(status)) { //驳回
            personalDao.updateCheckIdCardByOpenid("3", paa.getOpenid());
        }

        //TODO 通过用户
        return JsonResult.success("操作成功");
    }
}
