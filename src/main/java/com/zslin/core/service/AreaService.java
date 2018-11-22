package com.zslin.core.service;

import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.dto.QueryListDto;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.tools.JsonTools;
import com.zslin.basic.tools.QueryTools;
import com.zslin.core.dao.IAreaDao;
import com.zslin.core.dao.IAreaUserDao;
import com.zslin.core.model.Area;
import com.zslin.core.tools.AreaUserTools;
import com.zslin.core.tools.DivisionTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * 区域管理
 * Created by zsl on 2018/11/20.
 */
@Service
@AdminAuth(name = "区域管理", psn = "业务管理", orderNum = 3, type = "1", url = "/admin/area")
public class AreaService {

    @Autowired
    private IAreaDao areaDao;

    @Autowired
    private AreaUserTools areaUserTools;

    @Autowired
    private DivisionTools divisionTools;

    @Autowired
    private IAreaUserDao areaUserDao;

    public JsonResult list(String params) {
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<Area> res = areaDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));

        return JsonResult.getInstance().set("size", (int) res.getTotalElements()).set("data", res.getContent());
    }

    public JsonResult loadOne(String params) {
        try {
            Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
            Area obj = areaDao.findOne(id);
            return JsonResult.succ(obj);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    public JsonResult update(String params) {
        String phone = JsonTools.getJsonParam(params, "adminPhone");
        String name = JsonTools.getJsonParam(params, "adminName");
        String code = JsonTools.getJsonParam(params, "countyCode"); //countyCode
        Area a = areaDao.findByCountyCode(code);
        if(a.getAdminPhone()!=null && !"".equals(a.getAdminPhone())) {
            areaUserDao.updateStatus("0", code, a.getAdminPhone());
        }
        a.setAdminName(name);
        a.setAdminPhone(phone);
        a.setStatus("1");
        areaDao.save(a);

        areaUserTools.addAreaUser(code, a.getCountyName(), phone, name); //处理用户相关信息
        return JsonResult.success("操作成功");
    }

    /** 初始化区域 */
    public JsonResult init(String params) {
        divisionTools.initDivision(); //初始化区域
        return JsonResult.success("初始化完成");
    }
}
