package com.lcl.cloud.alibaba.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class OneGatewayFilter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();
        log.info("pre-filter-1=========={}", startTime);
        exchange.getAttributes().put("startTime", startTime);
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.info("========== post filter 1 =======");
            long startTime2 = exchange.getAttribute("startTime");
            log.info(" filter 1 run time ======= {}", System.currentTimeMillis() - startTime2);
        }));
    }
}
