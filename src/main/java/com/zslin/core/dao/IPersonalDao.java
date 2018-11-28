package com.zslin.core.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.core.model.Personal;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zsl on 2018/11/14.
 */
public interface IPersonalDao extends BaseRepository<Personal, Integer>, JpaSpecificationExecutor<Personal> {

    Personal findByOpenid(String openid);

    Personal findByPhone(String phone);

    Personal findByIdentity(String identity);

    @Query("UPDATE Personal p SET p.type=?1 WHERE p.openid=?2")
    @Modifying
    @Transactional
    void updateType(String type, String openid);

    //checkIdcard
    @Query("UPDATE Personal p SET p.checkIdcard=?1 WHERE p.openid=?2")
    @Modifying
    @Transactional
    void updateCheckIdCardByOpenid(String val, String openid);

    //checkCompany
    @Query("UPDATE Personal p SET p.checkCompany=?1 WHERE p.openid=?2")
    @Modifying
    @Transactional
    void updateCheckCompanyByOpenid(String val, String openid);

    @Query("UPDATE Personal p SET p.phone=?1, p.checkPhone='1' WHERE p.openid=?2")
    @Modifying
    @Transactional
    void updatePhone(String phone, String openid);
}
