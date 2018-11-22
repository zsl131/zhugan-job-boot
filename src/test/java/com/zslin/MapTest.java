package com.zslin;

import com.zslin.core.dto.AreaDto;
import com.zslin.core.tools.AreaCodeTools;
import com.zslin.core.tools.DistanceTools;
import com.zslin.map.tools.QQMapTools;
import com.zslin.wx.utils.QrUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zsl on 2018/7/5.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("zsl")
public class MapTest {

    @Autowired
    private QQMapTools qqMapTools;

    @Autowired
    private QrUtils qrUtils;

    @Test
    public void test05() {
//        qrUtils.generateQr("12346878");
        qrUtils.getminiqrQr("user_123456789");
    }

    @Test
    public void test04() {
        AreaDto dto = AreaCodeTools.getAreaCode(27.333302,103.697618);
        System.out.println("code====="+dto);
    }

    //列表距离最近的数据
    //SELECT *,(ROUND(6378137 * 2 * ASIN(SQRT(POW(SIN(((lat * PI()) / 180 - (27.317499 * PI()) / 180) / 2), 2) + COS((27.317499 * PI()) / 180) * COS((lat * PI()) / 180) * POW(SIN(((lng * PI()) / 180 - (103.707386 * PI()) / 180) / 2), 2))))) AS distance FROM c_bus_station ORDER BY distance ASC  LIMIT 0,10
    @Test
    public void test02() {
        Double length = DistanceTools.getDistance(27.317546,103.707381,27.318147,103.706115);
        System.out.println("=======length:==="+length);
    }

    @Test
    public void test01() {
//        qqMapTools.test01("公交车站", "昭通");
        qqMapTools.queryStations("公交站", 27.32027, 103.70594);
    }
}
