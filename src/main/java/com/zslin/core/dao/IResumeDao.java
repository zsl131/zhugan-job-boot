package com.zslin.core.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.core.model.Resume;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zsl on 2018/12/6.
 */
public interface IResumeDao extends BaseRepository<Resume, Integer>, JpaSpecificationExecutor<Resume> {

    Resume findByOpenid(String openid);

    @Query("UPDATE Resume r SET r.headimg=?1 WHERE r.openid=?2")
    @Modifying
    @Transactional
    void updateHeadimg(String headimg, String openid);

    @Query("UPDATE Resume r SET r.tags=?1 WHERE r.openid=?2")
    @Modifying
    @Transactional
    void updateTags(String tags, String openid);

    @Query("UPDATE Resume r SET r.workIds=?1, r.workNames=?2 WHERE r.openid=?3")
    @Modifying
    @Transactional
    void updateWorkTypes(String ids, String types, String openid);

    @Query("UPDATE Resume r SET r.status=?1 WHERE r.openid=?2")
    @Modifying
    @Transactional
    void updateStatus(String status, String openid);

    /** 修改被收藏的次数，可能增加、可能减少 */
    @Query(value = "UPDATE Resume r SET r.storeCount = r.storeCount+?1 WHERE r.id=?2")
    @Modifying
    @Transactional
    void updateStore(Integer amount, Integer id);

    @Query("UPDATE Resume r SET r.areaCode=?1, r.areaName=?2 WHERE r.openid=?3")
    @Modifying
    @Transactional
    void updateArea(String areaCode, String areaName, String openid);

    @Query("UPDATE Resume r SET r.eduId=?1, r.eduName=?2 WHERE r.openid=?3")
    @Modifying
    @Transactional
    void updateEdu(Integer eduId, String eduName, String openid);

    @Query("UPDATE Resume r SET r.status=?1, r.rejectReason=?2 WHERE r.id=?3")
    @Modifying
    @Transactional
    void updateVerify(String status, String reason, Integer id);

    @Query("UPDATE Resume r SET r.readCount = r.readCount + 1 WHERE r.id=?1")
    @Modifying
    @Transactional
    void updateReadCount(Integer id);
}
