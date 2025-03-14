package com.vertineko.shospital.interceptor;

import com.alibaba.fastjson2.JSON;
import com.vertineko.shospital.constant.RedisKeyConstant;
import com.vertineko.shospital.constant.Role;
import com.vertineko.shospital.constrain.Results;
import com.vertineko.shospital.constrain.errorDef.IErrorCode;
import com.vertineko.shospital.constrain.errorDef.error.AdminErrorCode;
import com.vertineko.shospital.usr.JwtUtil;
import com.vertineko.shospital.usr.UserDO;
import com.vertineko.shospital.usr.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PatientContextInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求头中获取存储的sessionKey
        String token = request.getHeader(RedisKeyConstant.REDIS_SESSION_KEY_PREFIX.getKey());
        //请求头中存在token
        log.info("token:{}", token);
        if (token != null && !token.isEmpty()) {
            String user_context = redisTemplate.opsForValue().get(RedisKeyConstant.ADMIN_LOGIN_KEY_PREFIX.getKey() + token);
            log.info("jwt:{}", user_context);
            if (user_context == null) {
                log.error("[ex]{}", AdminErrorCode.ADMIN_TOKEN_WRONG.getMessage());
                response.setStatus(401);
                String res = constructResString(AdminErrorCode.ADMIN_TOKEN_WRONG);
                response.getWriter().write(res);
                return false;
            }
            //将取出的JWT令牌进行验证
            if (JwtUtil.verify(user_context)) {
                Map<String, String> map = JwtUtil.parse(user_context);
                UserDO user = UserDO.builder()
                        .id(Long.valueOf(map.get("id")))
                        .username(map.get("username"))
                        .role(Role.PATIENT.getMsg())
                        .build();

                //防止在一些用户知道后端接口URL后用其他职责的账号恶意访问
                if (!user.getRole().equals(Role.PATIENT.getMsg())) {
                    log.error("[ex]{}", AdminErrorCode.USER_IS_NOT_ADMIN.getMessage());
                    response.setStatus(403);
                    String res = constructResString(AdminErrorCode.USER_IS_NOT_ADMIN);
                    response.getWriter().write(res);
                    return false;
                } else {
                    UserUtils.setUser(user);
                }
            } else {
                log.error("[ex]{}", AdminErrorCode.ADMIN_TOKEN_MISSING.getMessage());
                response.setStatus(401);
                String res = constructResString(AdminErrorCode.ADMIN_TOKEN_MISSING);
                response.getWriter().write(res);
                return false;
            }

        }
        return true;
    }

    private String constructResString(IErrorCode errorCode){
        return JSON.toJSONString(Results.fail(errorCode));
    }

}



