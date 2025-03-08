package com.vertineko.shospital.interceptor.filter;


import com.vertineko.shospital.constant.RedisKeyConstant;
import com.vertineko.shospital.usr.JwtUtil;
import com.vertineko.shospital.usr.UserDO;
import com.vertineko.shospital.usr.UserInfoDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class AdminContextFilter implements Filter {

    private StringRedisTemplate redisTemplate;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //从请求头中获取存储的sessionKey
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String key = request.getHeader(RedisKeyConstant.REDIS_SESSION_KEY_PREFIX.getKey());
        //请求头中存在key
        if (key != null && !key.isEmpty()) {
            String usercontext = redisTemplate.opsForValue().get(key);
            //将取出的JWT令牌进行验证
            if (JwtUtil.verify(usercontext)){
                Map<String, String> map = JwtUtil.parse(usercontext);
                UserDO user = UserDO.builder()
                        .id(Long.valueOf(map.get("id")))
                        .username(map.get("username"))
                        .role(map.get("role"))
                        .build();
                UserInfoDTO.setUser(user);
            }else{
                log.error("无效的token");
            }

            try{
                filterChain.doFilter(servletRequest, servletResponse);
            }finally {
                UserInfoDTO.clear();

            }
        }
    }
}
