package com.zslin.wx.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.wx.model.WxConfig;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by zsl on 2018/7/20.
 */
public interface IWxConfigDao extends BaseRepository<WxConfig, Integer> {

    @Query("FROM WxConfig")
    WxConfig loadOne();
}
