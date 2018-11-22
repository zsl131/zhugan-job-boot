package com.zslin.auth.dao;

import com.zslin.auth.model.Terminal;
import com.zslin.basic.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by zsl on 2018/10/29.
 */
public interface ITerminalDao extends BaseRepository<Terminal, Integer>, JpaSpecificationExecutor<Terminal> {

    Terminal findByToken(String token);
}
