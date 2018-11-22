package com.zslin.baidu.dao;

import com.zslin.baidu.model.UseRecord;
import com.zslin.basic.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by zsl on 2018/11/13.
 */
public interface IUseRecordDao extends BaseRepository<UseRecord, Integer>, JpaSpecificationExecutor<UseRecord> {
}
