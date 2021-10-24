package com.lcl.cloud.alibaba.sentinelgateway;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;
import java.util.*;

@SpringBootApplication
public class ConsumerSentinelGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerSentinelGatewayApplication.class, args);
        //route路由
        //initRule();
        //api路由限流
        initAPIRule();
        //API路由
        initCustomizedApis();
        //路由限流降级方法
        initBlockHandlers();
    }

    private static void initRule() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        GatewayFlowRule rule = gatewayFlowRule();
        rules.add(rule);
        GatewayRuleManager.loadRules(rules);
    }

    // 对名称为staff_route的路由规则进行限流
    private static GatewayFlowRule gatewayFlowRule() {
        // 定义一个Gateway限流规则实例
        GatewayFlowRule rule = new GatewayFlowRule();
        // 指定规则模式是route限流，其为默认值
        rule.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID);
        // 指定sentienl资源名称为 路由规则id
        rule.setResource("depart_route1");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(2);
        return rule;
    }

    // 路由限流降级方法
    private static void initBlockHandlers() {
        GatewayCallbackManager.setBlockHandler((exchange, th) -> {
            // 从请求中获取uri
            URI uri = exchange.getRequest().getURI();
            // 将响应数据写入到map
            Map<String, Object> map = new HashMap<>();
            map.put("uri", uri); map.put("msg", "访问量过大，请稍候重试");
            return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS) .contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(map));
        });
    }




    private static void initAPIRule() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        // 在这里指定了对哪些路由api进行限流
        GatewayFlowRule providerRule = gatewayFlowRule("depart_provider", 1);
        GatewayFlowRule consumerRule = gatewayFlowRule("depart_consumer", 2);
        rules.add(providerRule);
        rules.add(consumerRule);
        GatewayRuleManager.loadRules(rules);
    }

    private static void initCustomizedApis() {
        // 定义一个名称为 depart_provider 的路由api
        ApiDefinition providerApi = new ApiDefinition("depart_provider").setPredicateItems(
                new HashSet<ApiPredicateItem>() {{
                    add(new ApiPathPredicateItem().setPattern("/sentinel/get/**")
                            // 指定该路由api对于请求的匹配策略为 前辍匹配
                    .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX)); }});
        // 定义一个名称为 depart_consumer 的路由api
        ApiDefinition consumerApi = new ApiDefinition("depart_consumer").setPredicateItems(
                new HashSet<ApiPredicateItem>() {{
                    add(new ApiPathPredicateItem().setPattern("/provider/depart/get/2"));
                    add(new ApiPathPredicateItem().setPattern("/provider/depart/get/3")
                            // 指定该路由api对于请求的匹配策略为 精确匹配
                            .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_EXACT)); }});
        Set<ApiDefinition> definitions = new HashSet<>();
        definitions.add(providerApi);
        definitions.add(consumerApi);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }

    //定义网关限流规则
    private static GatewayFlowRule gatewayFlowRule(String apiName, int count) {
        GatewayFlowRule rule = new GatewayFlowRule();
        // 指定规则模式为路由api限流
        rule.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME );
        rule.setResource(apiName);
        //api名称
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(count);
        return rule;
    }


}
