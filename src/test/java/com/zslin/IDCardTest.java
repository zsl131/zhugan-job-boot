package com.zslin;

import com.zslin.baidu.dto.LicenseDto;
import com.zslin.baidu.tools.IDCardTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zsl on 2018/11/8.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("zsl")
public class IDCardTest {

    @Autowired
    private IDCardTools idCardTools;

    @Test
    public void test04() {
        String path = "D:/temp/7.jpg";
        LicenseDto dto1 = idCardTools.readLicenseToDto(path);
        System.out.println("=============7=========");
        System.out.println(dto1);

        System.out.println("=============8=========");
        System.out.println(idCardTools.readLicenseToDto("D:/temp/8.jpg"));

        System.out.println("=============9=========");
        System.out.println(idCardTools.readLicenseToDto("D:/temp/9.jpg"));

        System.out.println("=============10=========");
        System.out.println(idCardTools.readLicenseToDto("D:/temp/10.jpg"));
    }

    /*@Test
    public void test03() {
        String path = "D:/temp/id6.jpg";
        IDCardDto dto = IDCardTools.readToDto2(path);
        System.out.println(dto);
    }

    @Test
    public void test02() throws Exception {
        String path = "D:/temp/id6.jpg";
        IDCardDto dto = IDCardTools.readToDto(path);
        System.out.println(dto);
    }

    @Test
    public void test01() {
        String path = "D:/temp/id3.jpg";
        String res = IDCardTools.read(path);
        System.out.println(res);
    }*/
}
