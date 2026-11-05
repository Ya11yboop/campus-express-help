package com.campus.pickup.system.controller;

import com.campus.pickup.common.core.controller.BaseController;
import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.framework.annotation.RequiresRole;
import com.campus.pickup.framework.context.UserContext;
import com.campus.pickup.system.domain.SysCreditLog;
import com.campus.pickup.system.service.ISysCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 信誉分控制器
 */
@RestController
@RequestMapping("/credit")
public class SysCreditController extends BaseController {

    @Autowired
    private ISysCreditService creditService;

    @RequiresRole({"cs_admin", "operation_admin"})
    @GetMapping("/list")
    public AjaxResult list(SysCreditLog log) {
        if (UserContext.isCampusAdmin()) {
            log.setSchoolId(UserContext.getSchoolId());
        }
        startPage();
        List<SysCreditLog> list = creditService.selectLogList(log);
        return getDataTable(list);
    }

    /** 客服标记信誉分（参数：userId、changeScore、reason） */
    @RequiresRole("cs_admin")
    @PutMapping("/change")
    public AjaxResult change(@RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(String.valueOf(body.get("userId")));
        Integer changeScore = Integer.valueOf(String.valueOf(body.get("changeScore")));
        String reason = body.get("reason") == null ? "" : String.valueOf(body.get("reason"));
        creditService.changeScore(userId, changeScore, reason);
        return AjaxResult.success("信誉分更新成功");
    }
}
