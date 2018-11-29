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
import com.zslin.core.dao.ICompanyAuthApplyDao;
import com.zslin.core.dao.IPersonalDao;
import com.zslin.core.dao.IWalletDao;
import com.zslin.core.model.CompanyAuthApply;
import com.zslin.core.model.PersonalAuthApply;
import com.zslin.core.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * Created by zsl on 2018/11/19.
 */
@Service
@AdminAuth(name = "单位认证管理", psn = "业务管理", orderNum = 3, type = "1", url = "/admin/companyAuthApply")
public class CompanyAuthApplyService {

    @Autowired
    private ICompanyAuthApplyDao companyAuthApplyDao;

    @Autowired
    private IPersonalDao personalDao;

    @Autowired
    private IWalletDao walletDao;

    /** 列表 */
    public JsonResult list(String params) {
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<CompanyAuthApply> res = companyAuthApplyDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));

        return JsonResult.getInstance().set("size", res.getTotalElements()).set("data", res.getContent());
    }

    /** 审核 */
    public JsonResult verify(String params) {
        String status = JsonTools.getJsonParam(params, "status");
        Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
        String reason = JsonTools.getJsonParam(params, "reason");
        String username = JsonParamTools.getHeaderField(params, "username"); //登陆用户的用户名
        CompanyAuthApply paa = companyAuthApplyDao.findOne(id);
        paa.setReason(reason);
        paa.setStatus(status);
        paa.setVerifyDate(NormalTools.curDate());
        paa.setVerifyTime(NormalTools.curDatetime());
        paa.setVerifyLong(System.currentTimeMillis());
        paa.setVerifyUser(username);
        if("1".equals(status)) { //如果是通过，修改Personal属性
            personalDao.updateCheckCompanyByOpenid("1", paa.getOpenid());
            personalDao.updateType("2", paa.getOpenid()); //单位用户
            initWallet(paa); //设置钱包
        } else if("2".equals(status)) { //驳回
            personalDao.updateCheckCompanyByOpenid("3", paa.getOpenid());
        }

        //TODO 通过用户
        return JsonResult.success("操作成功");
    }

    /**  */
    private void initWallet(CompanyAuthApply caa) {
        String openid = caa.getOpenid();
        Wallet w = walletDao.findByOpenid(openid);
        if(w==null) {
            w = new Wallet();
        }
        w.setCompanyName(caa.getCompanyName());
        w.setCompanyNo(caa.getCompanyNo());
        walletDao.save(w);
    }
}
