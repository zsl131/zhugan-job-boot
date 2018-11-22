package com.zslin.core.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.core.model.Feedback;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by zsl on 2018/10/27.
 */
public interface IFeedbackDao extends BaseRepository<Feedback, Integer>, JpaSpecificationExecutor<Feedback> {
}
