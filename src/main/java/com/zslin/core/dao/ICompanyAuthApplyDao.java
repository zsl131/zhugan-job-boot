package com.zslin.core.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.core.model.CompanyAuthApply;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by zsl on 2018/11/27.
 */
public interface ICompanyAuthApplyDao extends BaseRepository<CompanyAuthApply, Integer>, JpaSpecificationExecutor<CompanyAuthApply> {

    CompanyAuthApply findByOpenid(String openid);
}
