package com.campus.pickup.system.util;

import com.campus.pickup.system.domain.SysUser;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息完善度工具
 */
public class UserProfileUtil {

    private static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     * 返回缺失的必填信息名称列表（按提示顺序）
     */
    public static List<String> missingFields(SysUser user) {
        List<String> missing = new ArrayList<>();
        if (isEmpty(user.getIdCard())) {
            missing.add("身份证");
        }
        if (isEmpty(user.getStudentNo())) {
            missing.add("学号");
        }
        if (user.getSchoolId() == null) {
            missing.add("学校");
        }
        if (isEmpty(user.getRealName())) {
            missing.add("姓名");
        }
        if (isEmpty(user.getPhone())) {
            missing.add("电话");
        }
        // 用户名/密码在微信自动注册时为空，完善信息时一并填写
        if (isEmpty(user.getPassword())) {
            missing.add("用户名");
            missing.add("密码");
        }
        return missing;
    }

    public static boolean isComplete(SysUser user) {
        return missingFields(user).isEmpty();
    }

    public static String buildMessage(SysUser user) {
        List<String> missing = missingFields(user);
        return String.join("、", missing) + "未完善，请完善信息后再使用该小程序";
    }
}
