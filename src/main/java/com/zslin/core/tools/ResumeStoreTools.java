package com.zslin.core.tools;

import com.zslin.core.dao.IResumeDao;
import com.zslin.core.model.Resume;
import com.zslin.core.model.ResumeStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zsl on 2018/12/21.
 */
@Component
public class ResumeStoreTools {

    @Autowired
    private IResumeDao resumeDao;

    public List<Resume> buildResumeStore(List<ResumeStore> list) {
        if(list==null || list.size()<=0) {return null;}
        return resumeDao.findByIds(generateIds(list));
    }

    /** 获取简历ID */
    private Integer[] generateIds(List<ResumeStore> list) {
        Integer [] ids = new Integer[list.size()];
        for(int i=0;i<list.size();i++) {
            ids[i] = list.get(i).getResumeId();
        }
        return ids;
    }
}
