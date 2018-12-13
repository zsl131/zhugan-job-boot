package com.zslin.core.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.core.model.ResumeStore;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zsl on 2018/12/12.
 */
public interface IResumeStoreDao extends BaseRepository<ResumeStore, Integer>, JpaSpecificationExecutor<ResumeStore> {

    ResumeStore findByOpenidAndResumeId(String openid, Integer resumeId);

    /** 删除对象 */
    @Modifying
    @Transactional
    void deleteByOpenidAndResumeId(String openid, Integer resumeId);
}
