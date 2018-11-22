package com.zslin.auth.dao;

import com.zslin.auth.model.TerminalSource;
import com.zslin.basic.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by zsl on 2018/10/29.
 */
public interface ITerminalSourceDao extends BaseRepository<TerminalSource, Integer>, JpaSpecificationExecutor<TerminalSource> {

    TerminalSource findByTokenAndCode(String token, String code);
}
