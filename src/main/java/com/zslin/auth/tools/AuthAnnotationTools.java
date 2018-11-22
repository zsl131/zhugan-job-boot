package com.zslin.auth.tools;

import com.zslin.auth.annotation.AuthAnnotation;
import com.zslin.auth.annotation.HasAuthAnnotation;
import com.zslin.auth.dao.IAuthSourceDao;
import com.zslin.auth.model.AuthSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 处理工具类
 * Created by zsl on 2018/10/29.
 */
@Component
public class AuthAnnotationTools {

    @Autowired
    private IAuthSourceDao authSourceDao;

    private static String [] packages = new String[]{"com/zslin/*/service/*Service.class"};

    public void init() {
//        List<AuthSource> list = new ArrayList<>();
        List<AuthSource> list = buildByPn(packages);
        for(AuthSource as : list) {
            if(authSourceDao.findBySnOrCode(as.getSn(), as.getCode())==null) {
                authSourceDao.save(as);
            }
        }
    }

    public List<AuthSource> buildList() {
        List<AuthSource> list = buildByPn(packages);
        return list;
    }

    private List<AuthSource> buildByPn(String ...pns) {
        List<AuthSource> list = new ArrayList<>();
        for(String pn : pns) {
            list.addAll(build(pn));
        }
        return list;
    }

    private List<AuthSource> build(String pn) {
        List<AuthSource> list = new ArrayList<>();
        try {
            //1、创建ResourcePatternResolver资源对象
            ResourcePatternResolver rpr = new PathMatchingResourcePatternResolver();
            //2、获取路径中的所有资源对象
            Resource[] ress = rpr.getResources(pn);
            //3、创建MetadataReaderFactory来获取工程
            MetadataReaderFactory fac = new CachingMetadataReaderFactory();
            //4、遍历资源
            for(Resource res:ress) {
                MetadataReader mr = fac.getMetadataReader(res);
                String cname = mr.getClassMetadata().getClassName();
                AnnotationMetadata am = mr.getAnnotationMetadata();
                if(am.hasAnnotation(HasAuthAnnotation.class.getName())) {
//                    build(am, cname);
                    list.addAll(build(am, cname));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<AuthSource> build(AnnotationMetadata am, String cname) {
        List<AuthSource> list = new ArrayList<>();
        Map<String, Object> mapp = am.getAnnotationAttributes(Service.class.getName());
        String serviceName = (String) mapp.get("value");
        String tempServiceName = cname.substring(cname.lastIndexOf(".")+1, cname.length());
        if(serviceName==null || "".equals(serviceName)) {
            serviceName = tempServiceName.substring(0, 1).toLowerCase() + tempServiceName.substring(1, tempServiceName.length());
        }

        Set<MethodMetadata> set = am.getAnnotatedMethods(AuthAnnotation.class.getName());
        for(MethodMetadata mm : set) {
            Map<String, Object> methodRes = mm.getAnnotationAttributes(AuthAnnotation.class.getName());
            String methodName = mm.getMethodName();
            AuthSource as = build(serviceName, methodName, methodRes);
            list.add(as);
        }
        return list;
    }

    private AuthSource build(String serviceName, String methodName, Map<String, Object> classRes) {
        String sn = serviceName+"."+methodName;
        String code = (String) classRes.get("code");
//        if(authSourceDao.findBySnOrCode(sn, code)==null) {
        AuthSource as = new AuthSource();
        as.setName((String) classRes.get("name"));
        as.setParams((String) classRes.get("params"));
        as.setCode(code);
        as.setStatus("1");
        as.setSn(sn);
//            authSourceDao.save(as);
        return as;
//        }
    }
}
