package com.campus.pickup.system.controller;

import com.alibaba.fastjson2.JSON;
import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.framework.context.UserContext;
import com.campus.pickup.framework.redis.CacheConstants;
import com.campus.pickup.framework.redis.RedisCacheUtil;
import com.campus.pickup.system.domain.SysComplaint;
import com.campus.pickup.system.domain.SysEvaluate;
import com.campus.pickup.system.domain.SysOrder;
import com.campus.pickup.system.domain.SysUser;
import com.campus.pickup.system.mapper.SysComplaintMapper;
import com.campus.pickup.system.mapper.SysEvaluateMapper;
import com.campus.pickup.system.mapper.SysOrderMapper;
import com.campus.pickup.system.mapper.SysUserMapper;
import com.campus.pickup.system.service.ISysComplaintService;
import com.campus.pickup.system.service.ISysEvaluateService;
import com.campus.pickup.system.service.ISysOrderService;
import com.campus.pickup.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据统计控制器（数据大屏/数据流图）
 */
@RestController
@RequestMapping("/dashboard")
public class SysDashboardController {

    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysOrderService orderService;
    @Autowired
    private ISysEvaluateService evaluateService;
    @Autowired
    private ISysComplaintService complaintService;

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysOrderMapper orderMapper;
    @Autowired
    private SysEvaluateMapper evaluateMapper;
    @Autowired
    private SysComplaintMapper complaintMapper;

    @Autowired
    private RedisCacheUtil redisCache;

    @GetMapping("/stats")
    public AjaxResult stats() {
        Long schoolId = UserContext.isCampusAdmin() ? UserContext.getSchoolId() : null;
        String key = CacheConstants.DASHBOARD + "stats:" + (schoolId == null ? "all" : schoolId);
        String cached = redisCache.get(key);
        if (cached != null) {
            return JSON.parseObject(cached, AjaxResult.class);
        }

        SysUser userQuery = new SysUser();
        userQuery.setSchoolId(schoolId);
        SysOrder orderQuery = new SysOrder();
        orderQuery.setSchoolId(schoolId);
        SysEvaluate evaluateQuery = new SysEvaluate();
        evaluateQuery.setSchoolId(schoolId);
        SysComplaint complaintQuery = new SysComplaint();
        complaintQuery.setSchoolId(schoolId);

        Map<String, Object> data = new HashMap<>();
        data.put("userCount", userService.selectUserList(userQuery).size());
        data.put("orderCount", orderService.selectOrderList(orderQuery).size());
        data.put("evaluateCount", evaluateService.selectEvaluateList(evaluateQuery).size());
        data.put("complaintCount", complaintService.selectComplaintList(complaintQuery).size());
        AjaxResult result = AjaxResult.success(data);
        // 统计数据缓存 60 秒（按学校隔离缓存，避免校区管理员看到全局数据）
        redisCache.set(key, result, 60);
        return result;
    }

    /** 近 7 天各指标趋势（用于首页统计卡迷你趋势图） */
    @GetMapping("/trend")
    public AjaxResult trend() {
        Long schoolId = UserContext.isCampusAdmin() ? UserContext.getSchoolId() : null;
        LocalDate today = LocalDate.now();
        LocalDate start = today.minusDays(6);
        String startDate = start.toString();

        List<String> dates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dates.add(start.plusDays(i).toString());
        }

        Map<String, Object> data = new HashMap<>();
        data.put("dates", dates);
        data.put("users", fillSeries(dates, userMapper.countUserByDay(startDate, schoolId)));
        data.put("orders", fillSeries(dates, orderMapper.countOrderByDay(startDate, schoolId)));
        data.put("evaluates", fillSeries(dates, evaluateMapper.countEvaluateByDay(startDate, schoolId)));
        data.put("complaints", fillSeries(dates, complaintMapper.countComplaintByDay(startDate, schoolId)));
        return AjaxResult.success(data);
    }

    private List<Integer> fillSeries(List<String> dates, List<Map<String, Object>> rows) {
        Map<String, Long> map = new HashMap<>();
        for (Map<String, Object> row : rows) {
            Object day = row.get("day");
            Object cnt = row.get("cnt");
            long v = cnt instanceof Number ? ((Number) cnt).longValue() : 0L;
            map.put(String.valueOf(day), v);
        }
        List<Integer> result = new ArrayList<>();
        for (String d : dates) {
            result.add(map.getOrDefault(d, 0L).intValue());
        }
        return result;
    }
}
