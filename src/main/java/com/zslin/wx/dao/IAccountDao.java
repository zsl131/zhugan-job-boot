package com.zslin.wx.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.wx.model.Account;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zsl on 2018/10/21.
 */
public interface IAccountDao extends BaseRepository<Account, Integer>, JpaSpecificationExecutor<Account> {

    Account findByOpenid(String openid);

    @Query("UPDATE Account a SET a.type=?2 WHERE a.id=?1")
    @Modifying
    @Transactional
    void updateType(Integer id, String type);

    @Query("SELECT COUNT(id) FROM Account ")
    Integer findAllCount();

    @Query("SELECT COUNT(id) FROM Account a WHERE a.status='1'")
    Integer findFollowCount();

    @Query("SELECT COUNT(id) FROM Account a WHERE a.createDate=?1 AND a.status='1'")
    Integer findFollowCountByDay(String day);

    @Query("SELECT a.openid FROM Account a WHERE a.type=?1")
    List<String> findOpenid(String type);

    @Query("UPDATE Account a SET a.status=?2 WHERE a.openid=?1")
    @Modifying
    @Transactional
    void updateStatus(String openid, String status);

    @Query("UPDATE Account a SET a.headimg=?1 WHERE a.openid=?2")
    @Modifying
    @Transactional
    void updateHeadimg(String headimg, String openid);
}
