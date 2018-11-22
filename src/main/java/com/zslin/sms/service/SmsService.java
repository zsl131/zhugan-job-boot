package com.zslin.sms.service;

import com.zslin.auth.annotation.AuthAnnotation;
import com.zslin.auth.annotation.HasAuthAnnotation;
import com.zslin.basic.dao.IUserDao;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.tools.JsonTools;
import com.zslin.basic.tools.RandomTools;
import com.zslin.core.dao.IPersonalDao;
import com.zslin.sms.tools.SmsTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 短信业务 S01
 * Created by zsl on 2018/9/26.
 */
@Service
@HasAuthAnnotation
public class SmsService {

    @Autowired
    private SmsTools smsTools;

    @Autowired
    private IPersonalDao personalDao;

    /** 发送验证码 */
    @AuthAnnotation(name = "小程序发送短信", code = "MINI-S01", params="{phone:''}")
    public JsonResult sendCode4Mini(String params) {
        String phone = JsonTools.getJsonParam(params, "phone");
        if(!isPhone(phone)) {
            return JsonResult.success("手机号码异常").set("flag", "0");
        } else {
            if(personalDao.findByPhone(phone)!=null) {
                return JsonResult.success("手机号已被占用").set("flag", "0");
            } else {
                String code = RandomTools.genCode4(); //生成验证码
//                smsTools.sendMsg(phone, "code", code);
                return JsonResult.success("发送成功，请注意查看手机短信").set("phone", phone).set("code", code).set("flag", "1");
            }
        }
    }

    private boolean isPhone(String phone) {
        if(phone==null || phone.length()!=11) {return false;}
        return (phone.startsWith("1") && phone.matches("[0-9]+"));
    }
}
