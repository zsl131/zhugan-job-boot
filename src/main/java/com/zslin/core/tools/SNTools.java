package com.zslin.core.tools;

import com.zslin.basic.tools.SecurityUtil;

import java.security.NoSuchAlgorithmException;

/**
 * Created by zsl on 2018/10/25.
 */
public class SNTools {

    /**
     * 生成SN
     * @param name
     * @return
     */
    public static String buildSn(String name) {
        try {
            String sn = SecurityUtil.md5(name);
            return sn;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
