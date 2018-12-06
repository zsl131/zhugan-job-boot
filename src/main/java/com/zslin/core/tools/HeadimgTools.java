package com.zslin.core.tools;

import com.zslin.basic.tools.ConfigTools;
import com.zslin.core.dao.IPersonalDao;
import com.zslin.core.dao.IResumeDao;
import com.zslin.core.model.Personal;
import com.zslin.wx.dao.IAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 头像处理
 * Created by zsl on 2018/12/5.
 */
@Component
public class HeadimgTools {

    @Autowired
    private IPersonalDao personalDao;

    @Autowired
    private ConfigTools configTools;

    @Autowired
    private IAccountDao accountDao;

    @Autowired
    private IResumeDao resumeDao;

    public void updateHeadimg(String openid, String headimg) {
//        System.out.println("openid::"+openid);
//        System.out.println("headimg::" + headimg);
        try {
            if(openid==null || "".equalsIgnoreCase(openid)) {return;}
            Personal p = personalDao.findByOpenid(openid);
            String oldImg = p.getHeadimg();
            p.setHeadimg(headimg);
            personalDao.save(p);
            accountDao.updateHeadimg(headimg, openid);
            resumeDao.updateHeadimg(headimg, openid);

            File f = new File(configTools.getUploadPath(false) + oldImg);
            System.out.println(f.getAbsolutePath());
            if(f.exists()) {f.delete();}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
