package com.zslin.core.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.core.model.WalletDetail;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by zsl on 2018/12/12.
 */
public interface IWalletDetailDao extends BaseRepository<WalletDetail, Integer>, JpaSpecificationExecutor<WalletDetail> {

    @Query("SELECT COUNT(id) FROM WalletDetail w WHERE w.openid=?1 AND w.objType=?2 AND w.objCode=?3")
    Integer countBuy(String openid, String objType, String objCode);
}
