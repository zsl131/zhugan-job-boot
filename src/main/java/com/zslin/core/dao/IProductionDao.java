package com.zslin.core.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.core.model.Production;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zsl on 2018/11/28.
 */
public interface IProductionDao extends BaseRepository<Production, Integer>, JpaSpecificationExecutor<Production> {

    /**
     * 获取产品
     * @param targetUser personal、company
     * @param status 0-停用，1-启用
     * @return
     */
    List<Production> findByTargetUserAndStatus(String targetUser, String status);

    /**
     * 获取产品
     * @param targetUser personal、company
     * @param type 1-查看次数，2-刷新次数
     * @param status 0-停用，1-启用
     * @return
     */
    List<Production> findByTargetUserAndTypeAndStatus(String targetUser, String type, String status);

    @Query("UPDATE Production p SET p.buyCount = p.buyCount+1 WHERE p.id=?1")
    @Modifying
    @Transactional
    void updateBuyCount(Integer id);
}
