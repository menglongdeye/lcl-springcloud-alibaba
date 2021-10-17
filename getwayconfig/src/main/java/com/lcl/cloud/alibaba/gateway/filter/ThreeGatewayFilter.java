package com.lcl.cloud.alibaba.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class ThreeGatewayFilter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("pre-filter-3=========={}");
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.info(" filter 3 run time ======= {}", System.currentTimeMillis());
        }));
    }
}
