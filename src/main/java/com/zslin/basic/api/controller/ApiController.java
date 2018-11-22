package com.zslin.basic.api.controller;

import com.zslin.auth.dao.IAuthSourceDao;
import com.zslin.auth.dao.ITerminalDao;
import com.zslin.auth.dao.ITerminalSourceDao;
import com.zslin.auth.model.AuthSource;
import com.zslin.auth.model.Terminal;
import com.zslin.basic.dto.JsonResult;
import com.zslin.basic.tools.JsonParamTools;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by zsl on 2018/7/3.
 */
@RestController
@RequestMapping(value = "api")
public class ApiController {

    @Autowired
    private ITerminalDao terminalDao;

    @Autowired
    private IAuthSourceDao authSourceDao;

    @Autowired
    private ITerminalSourceDao terminalSourceDao;

    @Autowired
    private BeanFactory factory;

    /**
     * 此接口调用的业务接口有一个名为"handle"的方法，此方法接受两个String类型的参数action和params
     *  - action - 具体的处理业务
     *  - params - 对应的json参数
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "baseRequest")
    public JsonResult baseRequest(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("auth-token"); //身份认证token
        String apiCode = request.getHeader("api-code"); //接口访问编码
        String from = request.getHeader("request-from"); //请求来源，可能是REACT、MINI、WX ...
        if(apiCode==null || "".equals(apiCode) || token==null || "".equals(token)) {
            return JsonResult.error("auth-token或api-code为空");
        }

        Terminal t = terminalDao.findByToken(token);
        if(t==null || !"1".equals(t.getStatus())) {
            return JsonResult.error("Token不存在或停用");
        }
        AuthSource as ;
        String sourceCode = null;
        if("0".equals(t.getLevel())) { //不受权限限制时传Code或Sn都可访问
            as = authSourceDao.findBySnOrCode(apiCode, apiCode);
            if(as==null) {sourceCode = apiCode;}
        } else {
            as = authSourceDao.findByCode(apiCode);
        }
        if(as==null && sourceCode == null) {
            return JsonResult.error("接口["+apiCode+"]不存在");
        }
        String temp = as!=null?as.getSn():sourceCode;
        String serviceName = temp.split("\\.")[0];
        String actionName = temp.split("\\.")[1];

        try {
//            String serviceName = apiCode.split("\\.")[0];
//            String actionName = apiCode.split("\\.")[1];
            Object obj = factory.getBean(serviceName);
            Method method ;
            boolean hasParams = false;
//            String params = request.getParameter("params");
            String params = JsonParamTools.rebuildParams(from, request);
            if(params==null || "".equals(params.trim())) {
                method = obj.getClass().getMethod(actionName);
            } else {
//                System.out.println(params);
//                System.out.println("==================================================================================");
//                params = Base64Utils.getFromBase64(params);
//                params = URLDecoder.decode(params, "utf-8");
//                System.out.println("============="+params);

//                params = JsonParamTools.rebuildParams(params, request);

//                System.out.println(params);

                method = obj.getClass().getMethod(actionName, params.getClass());
                hasParams = true;
            }
            JsonResult result;
            if(hasParams) {
                result = (JsonResult) method.invoke(obj, params);
            } else {
                result = (JsonResult) method.invoke(obj);
            }
//            System.out.println("result: "+result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.getInstance().fail("数据请求失败："+e.getMessage());
        }
    }
}
