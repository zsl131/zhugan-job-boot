package com.zslin.core.service;

import com.zslin.auth.annotation.AuthAnnotation;
import com.zslin.auth.annotation.HasAuthAnnotation;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.dto.QueryListDto;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.repository.SpecificationOperator;
import com.zslin.basic.tools.JsonTools;
import com.zslin.basic.tools.NormalTools;
import com.zslin.basic.tools.QueryTools;
import com.zslin.core.dao.IFeedbackDao;
import com.zslin.core.dto.AreaDto;
import com.zslin.core.model.Feedback;
import com.zslin.core.tools.AccountOpenidTools;
import com.zslin.core.tools.AreaCodeTools;
import com.zslin.wx.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * Created by zsl on 2018/10/27.
 */
@Service
@HasAuthAnnotation
public class FeedbackService {

    @Autowired
    private IFeedbackDao feedbackDao;

    @Autowired
    private AccountOpenidTools accountOpenidTools;

    @AuthAnnotation(name = "小程序获取显示的反馈信息", code = "M01", params = "{openid:''}")
    public JsonResult list4Mini(String params) {
//        System.out.println("------>"+params);
        String openid = JsonTools.getJsonParam(params, "openid");
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<Feedback> res = feedbackDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList(),
                new SpecificationOperator("status", "eq", "1", new SpecificationOperator("openid", "eq", openid, "or"))), //状态为显示),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));

        return JsonResult.getInstance().set("size", (int) res.getTotalElements()).set("data", res.getContent());
    }

    /**
     * 添加反馈
     * @param params {content:'', lat:0, lng:0, openid:'1'}
     * @return
     */
    @AuthAnnotation(name = "小程序添加反馈信息", code = "M02", params = "{content:'', lat:0, lng:0, openid:''}")
    public JsonResult addFeedback4Mini(String params) {
        String content = JsonTools.getJsonParam(params, "content");
        Account a = accountOpenidTools.getByParams(params);
        Feedback f = new Feedback();
        AreaDto dto = AreaCodeTools.getAreaByParams(params);
        if(dto!=null) {
            f.setAreaCode(dto.getCountyCode());
            f.setAreaName(dto.getCounty());
        }
        f.setContent(content);
        f.setCreateDate(NormalTools.curDate("yyyy-MM-dd"));
        f.setCreateLong(System.currentTimeMillis());
        f.setCreateTime(NormalTools.curDatetime());
        f.setStatus("0");
        if(a!=null) {
            f.setHeadimg(a.getHeadimg());
            f.setNickname(a.getNickname());
            f.setOpenid(a.getOpenid());
        }
        feedbackDao.save(f);
        return JsonResult.success("反馈成功").set("obj", f);
    }
}
