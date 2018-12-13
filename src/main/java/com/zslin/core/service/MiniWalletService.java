package com.zslin.core.service;

import com.zslin.auth.annotation.AuthAnnotation;
import com.zslin.auth.annotation.HasAuthAnnotation;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.tools.JsonTools;
import com.zslin.basic.tools.NormalTools;
import com.zslin.core.dao.IWalletDao;
import com.zslin.core.dao.IWalletDetailDao;
import com.zslin.core.model.Wallet;
import com.zslin.core.model.WalletDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 钱包管理MINI-C51 - MINI-C60
 * Created by zsl on 2018/12/12.
 */
@Service
@HasAuthAnnotation
public class MiniWalletService {

    @Autowired
    private IWalletDao walletDao;

    @Autowired
    private IWalletDetailDao walletDetailDao;

    @AuthAnnotation(name = "小程序通过Openid获取账户", code = "MINI-C51", params = "{openid:''}")
    public JsonResult loadByOpenid(String params) {
        String openid = JsonTools.getJsonParam(params, "openid");
        Wallet w = walletDao.findByOpenid(openid);
        return JsonResult.succ(w);
    }

    @AuthAnnotation(name = "小程序添加账户详情", code = "MINI-C52", params = "{openid:'', amount:0, isCharge:'1', level: 'personal', objType:'', objName:'', objCode:''}")
    public JsonResult addWalletDetail(String params) {
        try {
            String openid = JsonTools.getJsonParam(params, "openid");
            Integer amount = Integer.parseInt(JsonTools.getJsonParam(params, "amount")); //计量
            String isCharge = JsonTools.getJsonParam(params, "isCharge");
            String level = JsonTools.getJsonParam(params, "level"); //级别，personal、company
            String objType = JsonTools.getJsonParam(params, "objType"); //对象类型，personalRefresh、personalView、companyRefresh、companyView
            String objName = JsonTools.getJsonParam(params, "objName");
            String objCode = JsonTools.getJsonParam(params, "objCode");
            WalletDetail wd = new WalletDetail();
            wd.setAmount(amount);
            wd.setOpenid(openid);
            wd.setCreateDate(NormalTools.curDate());
            wd.setCreateLong(System.currentTimeMillis());
            wd.setCreateTime(NormalTools.curDatetime());
            wd.setIsCharge(isCharge);
            wd.setLevel(level);
            wd.setObjCode(objCode);
            wd.setObjType(objType);
            wd.setObjName(objName);
            walletDetailDao.save(wd);

            //修改
            String hql = "UPDATE Wallet w SET w."+objType+"=w."+objType+" + "+("1".equals(isCharge)?amount:(0-amount))+" WHERE w.openid=?1";
            walletDao.updateByHql(hql, openid);
            return JsonResult.success("记录成功");
        } catch (NumberFormatException e) {
            return JsonResult.success("记录失败");
        }
    }
}
