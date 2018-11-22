package com.zslin.baidu.tools;

import com.zslin.baidu.dao.IUseRecordDao;
import com.zslin.baidu.model.UseRecord;
import com.zslin.basic.tools.NormalTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zsl on 2018/11/13.
 */
@Component
public class UseRecordTools {

    @Autowired
    private IUseRecordDao useRecordDao;

    public void addRecord(String name, String nameCh, String result) {
        try { //添加请求记录
            UseRecord ur = new UseRecord();
            ur.setName(name);
            ur.setNameCh(nameCh);
            ur.setCreateDate(NormalTools.curDate("yyyy-MM-dd"));
            ur.setCreateTime(NormalTools.curDatetime());
            ur.setCreateLong(System.currentTimeMillis());
            ur.setResult(result);
            useRecordDao.save(ur);
        } catch (Exception e) {
        }
    }
}
