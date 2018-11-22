package com.zslin.wx.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.wx.model.MiniConfig;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by zsl on 2018/10/21.
 */
public interface IMiniConfigDao extends BaseRepository<MiniConfig, Integer> {

    @Query("FROM MiniConfig")
    MiniConfig loadOne();
}
