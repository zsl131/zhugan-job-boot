package com.zslin.baidu.tools;

import com.zslin.baidu.dto.IDCardDto;
import com.zslin.baidu.dto.LicenseDto;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;

/**
 * 身份证识别工具类
 * Created by zsl on 2018/11/8.
 */
@Component
public class IDCardTools {
//    private static final String APP_ID = "14727291";
//    private static final String API_KEY = "nEBNyfILCOrtcNn17NDRiCbx";
//    private static final String SECRET_KEY = "k5yXujY8CsVDTvAKOvtZc2CenD9lh9aF";

    @Autowired
    private UseRecordTools useRecordTools;

    /*public static String read(String filePath) {
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        JSONObject res = client.basicGeneral(filePath, new HashMap<>());
        System.out.println(res.toString(2));
        return res.toString();
    }*/

    public IDCardDto readToDto2(String filePath) {
        // 身份证识别url
        String idcardIdentificate = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        try {
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            // 识别身份证正面id_card_side=front;识别身份证背面id_card_side=back;
            String params = "id_card_side=front&" + URLEncoder.encode("image", "UTF-8") + "="
                    + URLEncoder.encode(imgStr, "UTF-8");
            /**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            String accessToken = AuthTokenService.getAuth();
//            System.out.println("====="+accessToken);
            String result = HttpUtil.post(idcardIdentificate, accessToken, params);
//            System.out.println(result);
            JSONObject jsonObj = new JSONObject(result).getJSONObject("words_result");
            String address = null;
            try { address = jsonObj.getJSONObject("住址").getString("words"); } catch (Exception e) { }
            String birthday = null;
            try { birthday = jsonObj.getJSONObject("出生").getString("words"); } catch (JSONException e) { }
            String name = null;
            try { name = jsonObj.getJSONObject("姓名").getString("words"); } catch (JSONException e) { }
            String cardNo = null;
            try { cardNo = jsonObj.getJSONObject("公民身份号码").getString("words"); } catch (JSONException e) { }
            String sex = null;
            try { sex = jsonObj.getJSONObject("性别").getString("words"); } catch (JSONException e) { }
            String nation = null;
            try { nation = jsonObj.getJSONObject("民族").getString("words"); } catch (Exception e) { }
            IDCardDto dto = new IDCardDto(name,sex,nation,birthday,address,cardNo);

            useRecordTools.addRecord("idcard", "身份证识别", jsonObj.toString()); //添加调用记录
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public LicenseDto readLicenseToDto(String filePath) {
        // 营业执照识别url
        String idcardIdentificate = "https://aip.baidubce.com/rest/2.0/ocr/v1/business_license";
        try {
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            // 识别身份证正面id_card_side=front;识别身份证背面id_card_side=back;
            String params = URLEncoder.encode("image", "UTF-8") + "="
                    + URLEncoder.encode(imgStr, "UTF-8");
            /**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            String accessToken = AuthTokenService.getAuth();
//            System.out.println("====="+accessToken);
            String result = HttpUtil.post(idcardIdentificate, accessToken, params);
            //System.out.println(result);
            //"单位名称": {"words": "献县创佳环卫设备有限公司"}, "法人": {"words": "王聪"}, "成立日期": {"words": "2016年03月03日"}, "有效期": {"words": "2036年03月02日"}}}
            JSONObject jsonObj = new JSONObject(result).getJSONObject("words_result");
            String address = null;
            try { address = jsonObj.getJSONObject("地址").getString("words"); } catch (Exception e) { }
            String money = null;
            try { money = jsonObj.getJSONObject("注册资本").getString("words"); } catch (JSONException e) { }
            String companyCode = null;
            try { companyCode = jsonObj.getJSONObject("社会信用代码").getString("words"); } catch (JSONException e) { }
            if(companyCode==null || "".equals(companyCode) || "无".equals(companyCode.trim())) {
                try { companyCode = jsonObj.getJSONObject("证件编号").getString("words"); } catch (JSONException e) { }
            }
            String companyName = null;
            try { companyName = jsonObj.getJSONObject("单位名称").getString("words"); } catch (JSONException e) { }
            String boss = null;
            try { boss = jsonObj.getJSONObject("法人").getString("words"); } catch (JSONException e) { }
            String startDate = null;
            try { startDate = jsonObj.getJSONObject("成立日期").getString("words"); } catch (Exception e) { }
            String endDate = null;
            try { endDate = jsonObj.getJSONObject("有效期").getString("words"); } catch (Exception e) { }
            LicenseDto dto = new LicenseDto(money, companyCode, companyName, boss, startDate, endDate, address);

            useRecordTools.addRecord("business_license", "营业执照识别", jsonObj.toString()); //添加调用记录
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*public static IDCardDto readToDto(String filePath) {
        try {
            AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
            JSONObject res = client.basicGeneral(filePath, new HashMap<>());
            System.out.println("---"+res.toString());
            JSONArray array = res.getJSONArray("words_result");
            String name = getName(array.getJSONObject(0)); //第一个是姓名
            String str2 = getCon(array.getJSONObject(1)); //第二个是性别和民族
            String sex = ""; //性别
            String nation = "";
            try {
                String [] array2 = str2.split("民族");
                sex = array2[0].replace("性别", "");
                nation = array2[1].replace("民族","");
            } catch (Exception e) {
            }
            String birthday = getBirthday(array.getJSONObject(2)); //第三个是出生
            String cardNo = getCardNo(array.getJSONObject(array.length()-1)); //最后一个是身份证号
            String address = getAddress(array);

            IDCardDto dto = new IDCardDto(name, sex, nation, birthday, address, cardNo);
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    /*private static String getAddress(JSONArray array) {
        try {
            StringBuffer sb = new StringBuffer();
            for(int i=3;i<array.length()-1;i++) {
                String con = getCon(array.getJSONObject(i));
                if(i==3) {con = con.replace("住址", "");}
                sb.append(con);
            }
            return sb.toString();
        } catch (Exception e) {
//            e.printStackTrace();
            return "";
        }
    }

    private static String getCardNo(JSONObject jsonObj) {
        try {
            String con = getCon(jsonObj);
            return con.replace("公民身份号码", "");
        } catch (Exception e) {
//            e.printStackTrace();
            return "";
        }
    }
    private static String getBirthday(JSONObject jsonObj) {
        try {
            String con = getCon(jsonObj);
            return con.replace("出生", "").replace("年", "-").replace("月", "-").replace("日", "");
        } catch (Exception e) {
//            e.printStackTrace();
            return "";
        }
    }

    private static String getName(JSONObject jsonObj) {
        try {
            String str = getCon(jsonObj);
            return str.replace("姓名", "");
        } catch (Exception e) {
//            e.printStackTrace();
            return "";
        }
    }

    private static String getCon(JSONObject jsonObj) {
        try {
            return jsonObj.getString("words");
        } catch (Exception e) {
            return "";
//            e.printStackTrace();
        }
    }*/
}
