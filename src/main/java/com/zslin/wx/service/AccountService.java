package com.zslin.wx.service;

import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.dto.QueryListDto;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.tools.JsonTools;
import com.zslin.basic.tools.QueryTools;
import com.zslin.wx.dao.IAccountDao;
import com.zslin.wx.model.Account;
import com.zslin.wx.tools.DatasTools;
import com.zslin.wx.tools.ExchangeTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * MINI-W01 - MINI-W10
 * Created by zsl on 2018/11/14.
 */
@Service
@AdminAuth(name = "小程序用户", psn = "小程序管理", orderNum = 3, type = "1", url = "/admin/account")
public class AccountService {

    @Autowired
    private IAccountDao accountDao;

    @Autowired
    private DatasTools datasTools;

    @Autowired
    private ExchangeTools exchangeTools;

    public JsonResult list(String params) {
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<Account> res = accountDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));

        return JsonResult.getInstance().set("size", res.getTotalElements()).set("datas", res.getContent());
    }

    public JsonResult updateType(String params) {
        try {
            Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
            String type = JsonTools.getJsonParam(params, "type");
            accountDao.updateType(id, type);
            return JsonResult.success("设置成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    /**
     * 同步微信用户
     * @param params id:1
     * @return
     */
    public JsonResult updateAccount(String params) {
        try {
            Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
            datasTools.updateAccount(id);
            Account a = accountDao.findOne(id);
            return JsonResult.succ(a);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    public JsonResult queryAccountByCode(String params) {
        try {
            String code = JsonTools.getJsonParam(params, "code");
            String openid = exchangeTools.getUserOpenId(code);
            Account account = accountDao.findByOpenid(openid);
            return JsonResult.succ(account);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }
}
