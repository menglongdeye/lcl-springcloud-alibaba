package com.lcl.cloud.alibaba.gateway;

import com.lcl.cloud.alibaba.gateway.filter.OneGatewayFilter;
import com.lcl.cloud.alibaba.gateway.filter.ThreeGatewayFilter;
import com.lcl.cloud.alibaba.gateway.filter.TwoGatewayFilter;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@SpringBootApplication
public class GetwayConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetwayConfigApplication.class, args);
    }

//    @Bean
//    public RouteLocator someRouteLocator(RouteLocatorBuilder locatorBuilder){
//        return locatorBuilder.routes().route(predicateSpec ->
//                predicateSpec.path("/b").uri("https://www.cnblogs.com").id("bkyn_route")).build();
//    }

//    @Bean
//    public RouteLocator someRouteLocator(RouteLocatorBuilder locatorBuilder){
//        ZonedDateTime time = LocalDateTime.now().minusDays(5).atZone(ZoneId.systemDefault());
//        ZonedDateTime time2 = LocalDateTime.now().plusDays(5).atZone(ZoneId.systemDefault());
//        return locatorBuilder.routes()
//                .route(predicateSpec -> predicateSpec.path("/b").uri("https://www.cnblogs.com").id("bkyn_route"))
//                //.route(predicateSpec -> predicateSpec.path("/provider/depart/**").and().after(time).uri("lb://provider02Nacosconfig").id("ribbon_route"))
//                //.route(predicateSpec -> predicateSpec.path("/provider/depart/**").and().before(time).uri("lb://provider02Nacosconfig").id("ribbon_route"))
//                //.route(predicateSpec -> predicateSpec.path("/provider/depart/**").and().between(time,time2).uri("lb://provider02Nacosconfig").id("ribbon_route"))
//                //.route(predicateSpec -> predicateSpec.path("/provider/depart/**").and().between(time,time2).and().cookie("lcl","mm").uri("lb://provider02Nacosconfig").id("ribbon_route"))
//                //.route(predicateSpec -> predicateSpec.path("/provider/depart/**").and().between(time,time2).and().cookie("lcl","mm").and().header("demokey","demovalue").uri("lb://provider02Nacosconfig").id("ribbon_route"))
//                //.route(predicateSpec -> predicateSpec.path("/provider/depart/**").and().between(time,time2).and().cookie("lcl","mm").and().header("demokey","demovalue").and().host("mypc:9001").uri("lb://provider02Nacosconfig").id("ribbon_route"))
//                //.route(predicateSpec -> predicateSpec.path("/provider/depart/**").and().query("name").and().query("age","18").uri("lb://provider02Nacosconfig").id("ribbon_route"))
//                //.route(predicateSpec -> predicateSpec.path("/provider/depart/**").and().remoteAddr("172.20.10.1/30").uri("lb://provider02Nacosconfig").id("ribbon_route"))
//                //.route(predicateSpec -> predicateSpec.path("/provider/depart/**").and().method("GET","POST").uri("lb://provider02Nacosconfig").id("ribbon_route"))
//                .route(predicateSpec -> predicateSpec.path("/demo").filters(fs -> fs.addRequestHeader("demokey","demovalue")).uri("http://localhost").id("header_filter"))
//
//                .build();
//    }


//    @Bean
//    public RouteLocator someRouteLocator(RouteLocatorBuilder locatorBuilder){
//        return locatorBuilder.routes()
//                //.route(predicateSpec -> predicateSpec.path("/demo").filters(fs -> fs.addRequestHeader("demokey","demovalue")).uri("http://localhost").id("header_filter"))
//                //.route(predicateSpec -> predicateSpec.path("/demo").filters(fs -> fs.addRequestParameter("name","lcl")).uri("http://localhost").id("header_filter"))
//                //.route(predicateSpec -> predicateSpec.path("/demo").filters(fs -> fs.addResponseHeader("demokey","demovalue2")).uri("http://localhost").id("header_filter"))
//                //.route(predicateSpec -> predicateSpec.path("/demo").filters(fs -> fs.prefixPath("demo")).uri("http://localhost").id("header_filter"))
//                //.route(predicateSpec -> predicateSpec.path("/demo").filters(fs -> fs.stripPrefix(2)).uri("http://localhost").id("header_filter"))
//                .route(predicateSpec -> predicateSpec.path("/demo").filters(fs -> fs.rewritePath("/red(?<segment>/?.*)", "${segment}")).uri("http://localhost").id("header_filter"))
//                .build();
//    }

//    @Bean
//    public RouteLocator someRouteLocator(RouteLocatorBuilder locatorBuilder){
//        return locatorBuilder.routes()
//                .route(predicateSpec -> predicateSpec.path("/**")
//                        .filters(fs -> fs.filter(new OneGatewayFilter())
//                                        .filter(new TwoGatewayFilter())
//                                        .filter(new ThreeGatewayFilter()))
//                        .uri("http://localhost:9000").id("selfFilter"))
//                .build();
//    }


//    /**
//     * 配置方式的熔断与降级
//     * @param locatorBuilder
//     * @return
//     */
//    @Bean
//    public RouteLocator someRouteLocator(RouteLocatorBuilder locatorBuilder){
//        return locatorBuilder.routes()
//                .route(predicateSpec -> predicateSpec.path("/demo/time")
//                        .filters(fs -> fs.circuitBreaker(config -> {
//                            config.setName("myCircuitBreaker");
//                        config.setFallbackUri("forward:/fb");}
//                        ))
//                        .uri("http://localhost:9000").id("selfFilter"))
//                .build();
//    }



    @Bean
    public IRule loadBalancerRule(){
        return new RandomRule();
    }
}
