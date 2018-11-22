package com.zslin.core.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.core.model.PersonalAuthApply;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by zsl on 2018/11/14.
 */
public interface IPersonalAuthApplyDao extends BaseRepository<PersonalAuthApply, Integer>, JpaSpecificationExecutor<PersonalAuthApply> {

    PersonalAuthApply findByOpenid(String openid);

    PersonalAuthApply findByIdentity(String identity);
}
