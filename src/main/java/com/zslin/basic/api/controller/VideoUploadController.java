package com.zslin.basic.api.controller;

import com.zslin.basic.dto.JsonResult;
import com.zslin.core.dao.IPersonalDao;
import com.zslin.qiniu.dto.MyPutRet;
import com.zslin.qiniu.model.QiniuConfig;
import com.zslin.qiniu.tools.MyFileTools;
import com.zslin.qiniu.tools.QiniuConfigTools;
import com.zslin.qiniu.tools.QiniuUploadTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by zsl on 2018/12/1.
 */
@RestController
@RequestMapping(value = "api/video")
public class VideoUploadController {

    @Autowired
    private QiniuUploadTools qiniuUploadTools;

    @Autowired
    private QiniuConfigTools qiniuConfigTools;

    @Autowired
    private IPersonalDao personalDao;

    @RequestMapping(value = "upload")
    public JsonResult upload(@RequestParam("file")MultipartFile[] multipartFile, Integer id, String phone) throws IOException {
        if(multipartFile!=null && multipartFile.length>=1) {
            MultipartFile mf = multipartFile[0];
//                System.out.println(mf.getName() + "===" + mf.getOriginalFilename() + "===" + mf.getContentType());
            FileInputStream fileInputStream = (FileInputStream) mf.getInputStream();

            MultipartFile file = multipartFile[0];
            String fileName = file.getOriginalFilename();
//                    System.out.println("========fileName::"+fileName);
            if (fileName != null && !"".equalsIgnoreCase(fileName.trim()) && MyFileTools.isVideoFile(fileName)) {

                String fileType = MyFileTools.getFileType(fileName);

                MyPutRet putRet = qiniuUploadTools.upload(file.getInputStream(), phone+fileType);
                if(putRet==null) {
                    return JsonResult.error("上传失败");
                }
                String videoUrl = rebuildUrl(putRet.getKey());
                personalDao.updateVideo(videoUrl, id); //修改Personal对象
                System.out.println(putRet);
                return JsonResult.succ(putRet);
            }
        }
        return JsonResult.error("上传不成功");
    }

    private String rebuildUrl(String key) {
        try {
            QiniuConfig config = qiniuConfigTools.getQiniuConfig();
            String url = config.getUrl();
            if(url==null) {return key;}
            url = url.endsWith("/")?(url+key):(url+"/"+key);
            return url;
        } catch (Exception e) {
            return key;
        }
    }
}
