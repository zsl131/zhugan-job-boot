package com.zslin;

import com.zslin.basic.dto.QueryListDto;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.repository.SpecificationOperator;
import com.zslin.basic.tools.QueryTools;
import com.zslin.core.dao.IFeedbackDao;
import com.zslin.core.model.Feedback;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.beans.FeatureDescriptor;

/**
 * Created by zsl on 2018/8/6.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("zsl")
public class JpaTest {

    @Autowired
    private IFeedbackDao feedbackDao;

    @Test
    public void test04() {
        QueryListDto qld = new QueryListDto(0, 2, "id_d");

        Integer [] ids = new Integer[]{1, 2, 3};

        String openid = "orLIDuFuyeOygL0FBIuEilwCF1lU1234";
        Page<Feedback> res = feedbackDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));

        System.out.println(res.getTotalElements());
    }

    @Test
    public void test03() {
        QueryListDto qld = new QueryListDto(0, 2, "id_d");

        Integer [] ids = new Integer[]{1, 2, 3};

        String openid = "orLIDuFuyeOygL0FBIuEilwCF1lU1234";
        Page<Feedback> res = feedbackDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList(),
                new SpecificationOperator("id", "in", ids)),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));

        System.out.println(res.getTotalElements());
    }

    @Test
    public void test01() {

        QueryListDto qld = new QueryListDto(0, 2, "id_d");

        String openid = "o7u6r5tcy9f0sC4F2DHml15N9I7g";

        Page<Feedback> res = feedbackDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList(),
                new SpecificationOperator("status", "eq", "1", new SpecificationOperator("openid", "eq", openid, "or")) //状态为显示
//                    , new SpecificationOperator("openid", "eq", openid, "or") //暂时去掉此功能
                ),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));

        System.out.println(res.getTotalElements());
    }

    @Test
    public void test02() {
        String openid = "o7u6r5tcy9f0sC4F2DHml15N9I7g";
        QueryListDto qld = new QueryListDto(0, 2, "id_d");
        Page<FeatureDescriptor> res = feedbackDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList(),
                new SpecificationOperator("id", "eq", 3) //活动详情Id
                , new SpecificationOperator("status", "eq", "1",  new SpecificationOperator("openid", "eq", openid, "or")) //状态为显示
//                    , new SpecificationOperator("openid", "eq", openid, "or") //或openid为当前用户， 暂时去掉此功能
                ,new SpecificationOperator("id", "le", 1)),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));

        System.out.println(res.getTotalElements());
    }
}
