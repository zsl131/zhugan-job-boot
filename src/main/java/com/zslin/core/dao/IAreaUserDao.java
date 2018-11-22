package com.zslin.core.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.core.model.AreaUser;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zsl on 2018/11/21.
 */
public interface IAreaUserDao extends BaseRepository<AreaUser, Integer>, JpaSpecificationExecutor<AreaUser> {

    @Query("UPDATE AreaUser SET status=?1 WHERE id=?2")
    @Modifying
    @Transactional
    void updateStatus(String status, Integer id);

    @Query("UPDATE AreaUser a SET a.status=?1 WHERE a.countyCode=?2 AND a.phone=?3")
    @Modifying
    @Transactional
    void updateStatus(String status, String countyCode, String phone);
}
