package com.zslin.auth.dao;

import com.zslin.auth.model.AuthSource;
import com.zslin.basic.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by zsl on 2018/10/29.
 */
public interface IAuthSourceDao extends BaseRepository<AuthSource, Integer>, JpaSpecificationExecutor<AuthSource> {

    AuthSource findBySn(String sn);

    AuthSource findByCode(String code);

    AuthSource findBySnOrCode(String sn, String code);
}
