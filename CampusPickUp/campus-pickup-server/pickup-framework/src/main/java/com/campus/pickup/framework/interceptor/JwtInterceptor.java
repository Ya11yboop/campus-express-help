package com.campus.pickup.framework.interceptor;

import com.campus.pickup.framework.annotation.RequiresRole;
import com.campus.pickup.framework.context.UserContext;
import com.campus.pickup.framework.jwt.LoginUser;
import com.campus.pickup.framework.jwt.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT 认证 + 角色权限拦截器
 *
 * @author campus-pickup
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        if (token == null || token.isEmpty()) {
            return writeJson(response, 401, "未登录或登录已过期");
        }

        LoginUser loginUser = tokenService.getLoginUser(token);
        if (loginUser == null) {
            return writeJson(response, 401, "登录已过期，请重新登录");
        }
        UserContext.set(loginUser);

        // 角色权限校验
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            RequiresRole rr = hm.getMethodAnnotation(RequiresRole.class);
            if (rr == null) {
                rr = hm.getBeanType().getAnnotation(RequiresRole.class);
            }
            if (rr != null && rr.value().length > 0) {
                boolean allowed = false;
                for (String role : rr.value()) {
                    if (UserContext.hasRole(role)) {
                        allowed = true;
                        break;
                    }
                }
                if (!allowed) {
                    return writeJson(response, 403, "无权限访问该功能");
                }
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear();
    }

    private boolean writeJson(HttpServletResponse response, int code, String msg) throws Exception {
        response.setStatus(code);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":" + code + ",\"msg\":\"" + msg + "\"}");
        return false;
    }
}
