package com.zslin.core.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.core.model.Wallet;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by zsl on 2018/11/28.
 */
public interface IWalletDao extends BaseRepository<Wallet, Integer>, JpaSpecificationExecutor<Wallet> {

    Wallet findByOpenid(String openid);
}
