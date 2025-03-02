package com.vertineko.shospital.filter;

import com.vertineko.shospital.config.GatewayConfiguration;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class TokenValidateGatewayFilterFactory extends AbstractGatewayFilterFactory<GatewayConfiguration> {

    public TokenValidateGatewayFilterFactory() {
        super(GatewayConfiguration.class);
    }

    @Override
    public GatewayFilter apply(GatewayConfiguration config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String requestPath = request.getURI().getPath();
            String token = request.getHeaders().getFirst("token");
            //不在白名单内
            if (!isWhitePath(requestPath, config.getWhitePathList())){
                //没有token 或者 token 字段为空
                if(token == null || token.isEmpty()) {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.writeWith(Mono.fromSupplier(() -> {
                        DataBufferFactory bufferFactory = response.bufferFactory();
                        return bufferFactory.wrap("登录失效".getBytes());
                    }));
                }
                //有token, 则让请求都带上token去访问接口
                ServerHttpRequest.Builder builder = request.mutate().headers(httpHeaders -> {
                    httpHeaders.set("token", token);
                });
                return chain.filter(exchange.mutate().request(builder.build()).build());
            }
            return chain.filter(exchange);
        });
    }




    public boolean isWhitePath(String path, List<String> whiteList){
        return (path != null && whiteList.stream().anyMatch(path::startsWith));
    }
}
