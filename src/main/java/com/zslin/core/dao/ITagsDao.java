package com.zslin.core.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.core.model.Tags;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by zsl on 2018/11/14.
 */
public interface ITagsDao extends BaseRepository<Tags, Integer>, JpaSpecificationExecutor<Tags> {

    Tags findByText(String text);
}
