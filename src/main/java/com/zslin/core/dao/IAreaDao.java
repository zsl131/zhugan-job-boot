package com.zslin.core.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.core.model.Area;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by zsl on 2018/11/20.
 */
public interface IAreaDao extends BaseRepository<Area, Integer>, JpaSpecificationExecutor<Area> {

    Area findByCountyCode(String countyCode);

    List<Area> findByStatus(String status);
}
