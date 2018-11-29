package com.zslin.core.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.core.model.Tags;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zsl on 2018/11/14.
 */
public interface ITagsDao extends BaseRepository<Tags, Integer>, JpaSpecificationExecutor<Tags> {

    Tags findByText(String text);

    @Query("UPDATE Tags t SET t.count=t.count+1 WHERE t.id=?1")
    @Modifying
    @Transactional
    void plusCount(Integer id);
}
