package com.zslin.basic.tools;

import com.zslin.auth.tools.AuthAnnotationTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 项目启动时执行
 * Created by zsl on 2018/10/29.
 */
@Component
public class MyApplicationStarter implements ApplicationRunner {

    @Autowired
    private AuthAnnotationTools authAnnotationTools;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        authAnnotationTools.init();
    }
}
