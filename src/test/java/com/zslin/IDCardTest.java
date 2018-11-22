package com.zslin;

import org.junit.runner.RunWith;
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
