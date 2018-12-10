package com.zslin.core.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.core.model.DataEdu;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by zsl on 2018/12/8.
 */
public interface IDataEduDao extends BaseRepository<DataEdu, Integer>, JpaSpecificationExecutor<DataEdu> {

    DataEdu findByName(String name);
}
