package com.zslin;

import com.alibaba.fastjson.JSONObject;
import com.qq.weixin.mp.aes.MiniProgramTools;
import com.zslin.basic.tools.SecurityUtil;
import com.zslin.core.tools.DivisionTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by zsl on 2018/7/5.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("zsl")
public class NormalTest {

    @Autowired
    private DivisionTools divisionTools;

    @Test
    public void test05() {
        divisionTools.initDivision();
    }

    @Test
    public void test04() {
        System.out.println(isPhone("1592506125 5"));
        System.out.println(isPhone(null));
        System.out.println(isPhone(""));
        System.out.println(isPhone("1592506125s"));
        System.out.println(isPhone("15925061256"));
        System.out.println(isPhone("25925061256"));
    }

    private boolean isPhone(String phone) {
        if(phone==null || phone.length()!=11) {return false;}
        return (phone.startsWith("1") && phone.matches("[0-9]+"));
    }

    @Test
    public void test03() throws Exception {
        String pwd = SecurityUtil.md5("root", "123456789");
        System.out.println(pwd);
    }

    @Test
    public void test01() {
        System.out.println("==============");
    }

    @Test
    public void test02() throws Exception {
//        String sessionKey = "3dNtfmb\\/DhfTADFjoH1P8A==";
//        String pwd ="0vbf4b4NcEmDsCvCP3syJFLZ0D/MWAJrnKmxV2lQFwHBQ1HyBw3r9sYDNAcz3cafe75ZeeGacwwWSWmUzZBJoDIVmwNIPT2rBmVKp7ho/K0RbjNyGJau3hG6WAtYLIPfvya04ivabu/VxqUgfOWUnCatJnIsH4S1SlXBHoVNbhTa2tTOZLp+BpM7hcHl8Dc+a7zF0Mbbk1YsFAe2b9nq0hqSAaFWUsbgMl5MNYeDAc+qEJRzqj/McpRLwFXxQO1tpcMh8sHRBlPeOwJYQQ4VH8Vn58dFt8U8LiYtxmaf979aXDMgAR/hMWbNuOsS+7IGh2BmzuHZPQSVn+fq3c7hKaxTeXaSnupx6p1Dep4riAg3/qxmFqR6GCNnN3NsrKR2USsdXzfxu0Df5YjE6F4ZxHIYhw2fFhyBosuld0IMmYa0DaABnBcAVOCcDramfr/T8WzD9gvOUnbuT+1Uz4EcFxQhlx7qp9/87WqNKNWI83w=";
//        String iv = "awKgEQz+UEc0jcOj7b22yw==";
//        String rawData = "{\"nickName\":\"钟述林\",\"gender\":1,\"language\":\"zh_CN\",\"city\":\"Zhaotong\",\"province\":\"Yunnan\",\"country\":\"China\",\"avatarUrl\":\"https://wx.qlogo.cn/mmopen/vi_32/FISFzWr1GckMePTyvHiaILCTLpIGPr8SVjTWfwvBxDsRFkvWjG4y57ByAaPfUa1gWRYGtRBZveNOYHibflhF4ogg/132\"}";
//        String signature = "1c7702fcc7c89ec9aeb9f4ac582965af6a9bb137";


        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:/temp/123.txt")));
        String pwd = br.readLine();
        String iv = br.readLine();
        String sessionKey = br.readLine();

        JSONObject jsonObj = MiniProgramTools.getUserInfo(pwd, sessionKey, iv);
        System.out.println(jsonObj.toString());
    }
}
