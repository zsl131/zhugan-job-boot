package com.zslin.basic.api.controller;

import com.zslin.basic.tools.NormalTools;
import com.zslin.core.tools.DocWordTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * Created by zsl on 2018/10/19.
 */
@RestController
@RequestMapping(value = "api/test")
public class TestController {

    @Autowired
    private DocWordTools docWordTools;

    @GetMapping(value = "list")
    public @ResponseBody String list(String name) {
        return "server return :: "+ (name==null?"default":name);
    }

    @GetMapping(value = "downloadDoc")
    public @ResponseBody String downloadDoc(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("UTF-8");
            String fileName = "接口说明文档-"+ NormalTools.curDate("yyyyMMdd")+".docx";
            fileName = URLEncoder.encode(fileName,"UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            docWordTools.exportDoc(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return "下载失败："+e.getMessage();
        }
        return "下载成功";
    }
}
