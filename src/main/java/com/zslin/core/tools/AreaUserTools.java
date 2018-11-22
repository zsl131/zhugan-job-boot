package com.zslin.core.tools;

import com.zslin.basic.dao.IRoleDao;
import com.zslin.basic.dao.IUserDao;
import com.zslin.basic.dao.IUserRoleDao;
import com.zslin.basic.model.Role;
import com.zslin.basic.model.User;
import com.zslin.basic.model.UserRole;
import com.zslin.basic.tools.NormalTools;
import com.zslin.basic.tools.SecurityUtil;
import com.zslin.core.dao.IAreaUserDao;
import com.zslin.core.model.AreaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

/**
 * 区域用户管理工具
 * Created by zsl on 2018/11/21.
 */
@Component
public class AreaUserTools {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IAreaUserDao areaUserDao;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IUserRoleDao userRoleDao;

    private static final String AREA_ROLE = "AREA_ADMIN_ROLE"; //区域管理员角色

    public void addAreaUser(String areaCode, String areaName, String phone, String name) {
        AreaUser au = new AreaUser();
        au.setName(name);
        au.setCountyCode(areaCode);
        au.setCountyName(areaName);
        au.setPhone(phone);
        au.setUsername(phone);
        au.setStatus("1");
        User u = initUser(phone, name);
        au.setUserId(u.getId());
        au.setCreateTime(NormalTools.curDatetime());
        areaUserDao.save(au);
    }

    private User initUser(String phone, String name) {
        User user = userDao.findByUsername(phone);
        if(user==null) {
            user = new User();
            user.setCreateDate(NormalTools.curDate());
            user.setPhone(phone);
            user.setUsername(phone);
            user.setStatus(1);
            user.setIsAdmin("0");
            user.setNickname(name);
            try {
                user.setPassword(SecurityUtil.md5(phone, phone));
            } catch (Exception e) {
            }
            userDao.save(user);

            UserRole ur = new UserRole();
            ur.setUid(user.getId());
            ur.setRid(getRole().getId());
            userRoleDao.save(ur); //配置角色
        }

        return user;
    }

    private Role getRole() {
        Role r = roleDao.findBySn(AREA_ROLE);
        if(r==null) {
            r = new Role();
            r.setName("区域管理员");
            r.setSn(AREA_ROLE);
            roleDao.save(r);
        }
        return r;
    }
}
