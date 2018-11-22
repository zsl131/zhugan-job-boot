package com.zslin.wx.tools;

import com.zslin.wx.dao.IMiniConfigDao;
import com.zslin.wx.model.MiniConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zsl on 2018/10/21.
 */
@Component
public class MiniConfigTools {

    @Autowired
    private IMiniConfigDao miniConfigDao;

    private static MiniConfig config;

    public MiniConfig getMiniConfig() {
        if(config==null) {
            config = miniConfigDao.loadOne();
        }
        return config;
    }
}
