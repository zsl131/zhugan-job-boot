package com.zslin;

import com.zslin.core.tools.DocWordTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zsl on 2018/10/30.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("zsl")
public class DocTest {

    @Autowired
    private DocWordTools docWordTools;

    @Test
    public void test01() throws Exception {
        docWordTools.exportDoc("D:/temp/123.docx");
    }
}
