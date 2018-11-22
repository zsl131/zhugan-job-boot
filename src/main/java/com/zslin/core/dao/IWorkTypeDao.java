package com.zslin.core.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.core.model.WorkType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by zsl on 2018/11/14.
 */
public interface IWorkTypeDao extends BaseRepository<WorkType, Integer>, JpaSpecificationExecutor<WorkType> {

    WorkType findByName(String name);
}
