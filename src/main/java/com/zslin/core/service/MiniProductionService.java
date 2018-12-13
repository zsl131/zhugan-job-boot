package com.zslin.core.service;

import com.zslin.auth.annotation.AuthAnnotation;
import com.zslin.auth.annotation.HasAuthAnnotation;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.tools.JsonTools;
import com.zslin.basic.tools.NormalTools;
import com.zslin.core.dao.IProductionDao;
import com.zslin.core.dao.IWalletDao;
import com.zslin.core.dao.IWalletDetailDao;
import com.zslin.core.model.Production;
import com.zslin.core.model.WalletDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品管理MINI-C61 - MINI-C70
 * Created by zsl on 2018/12/13.
 */
@Service
@HasAuthAnnotation
public class MiniProductionService {

    @Autowired
    private IProductionDao productionDao;

    @Autowired
    private IWalletDao walletDao;

    @Autowired
    private IWalletDetailDao walletDetailDao;

    private static final String OBJ_TYPE = "charge";

    @AuthAnnotation(name = "小程序获取产品列表", code = "MINI-C61", params = "{target:''}")
    public JsonResult listProduction(String params) {
        String target = JsonTools.getJsonParam(params, "target");
        List<Production> list = productionDao.findByTargetUserAndStatus(target, "1");
        return JsonResult.success().set("data", list);
    }

    @AuthAnnotation(name = "小程序获取产品信息", code = "MINI-C62", params = "{id: 0}")
    public JsonResult loadProduction(String params) {
        Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
        Production p = productionDao.findOne(id);
        return JsonResult.succ(p);
    }

    @AuthAnnotation(name = "小程序购买产品", code = "MINI-C63", params = "{id:0, openid:''}")
    public JsonResult buyProduction(String params) {
        String openid = JsonTools.getJsonParam(params, "openid");
        Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
        Production p = productionDao.findOne(id);
        Integer limit = p.getAmountLimit();
        if(limit>0) {
            Integer count = walletDetailDao.countBuy(openid, OBJ_TYPE, id+"");
            if(count!=null && count>=limit) {
                return JsonResult.error("超过购买次数，不可再购买");
            }
        }
        String objType;
        if("personal".equalsIgnoreCase(p.getTargetUser())) {
            if("1".equals(p.getType())) {objType = "personalViewCount";}
            else {objType = "personalRefreshCount";}
        } else {
            if("1".equals(p.getType())) {objType = "companyViewCount";}
            else {objType = "companyRefreshCount";}
        }
        insertWalletDetail(p, openid);
        //修改
        String hql = "UPDATE Wallet w SET w."+objType+"=w."+objType+" + "+p.getWorthCount()+" WHERE w.openid=?1";
        walletDao.updateByHql(hql, openid);
        productionDao.updateBuyCount(id); //修改购买次数
        return JsonResult.success("购买成功");
    }

    private void insertWalletDetail(Production p, String openid) {
        WalletDetail wd = new WalletDetail();
        wd.setAmount(p.getWorthCount());
        wd.setOpenid(openid);
        wd.setCreateDate(NormalTools.curDate());
        wd.setCreateLong(System.currentTimeMillis());
        wd.setCreateTime(NormalTools.curDatetime());
        wd.setIsCharge("1");
        wd.setLevel(p.getTargetUser());
        wd.setObjCode(p.getId()+"");
        wd.setObjType(OBJ_TYPE);
        wd.setObjName(p.getName());
        walletDetailDao.save(wd);
    }
}
