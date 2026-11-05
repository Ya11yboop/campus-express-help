package com.campus.pickup.system.service;

import com.campus.pickup.system.domain.SysNotice;

import java.util.List;

/**
 * 公告服务接口
 */
public interface ISysNoticeService {

    List<SysNotice> selectNoticeList(SysNotice notice);

    SysNotice selectNoticeById(Long noticeId);

    List<SysNotice> selectNoticeAll(Long schoolId);

    int insertNotice(SysNotice notice);

    int updateNotice(SysNotice notice);

    int deleteNoticeById(Long noticeId);
}
