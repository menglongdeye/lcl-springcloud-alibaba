package com.lcl.cloud.alibaba.sentinel;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.lcl.cloud.alibaba.sentinel.service")//开启当前服务支持Feign客户端，作用扫描所有客户端接口
public class ConsumerSentinelApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerSentinelApplication.class, args);
        initRule();
    }

    public static void initRule() {
        //初始化熔断策略
        //initDegradeRule();
        //初始化流控策略
        //initFlowRule();
        //初始化黑白名单
        //initAuthorityRule();
        //热点参数
        //initParamFlowRule();
    }

    //初始化规则
    public static void initParamFlowRule() {
        List<ParamFlowRule> rules = new ArrayList<>();
        ParamFlowRule rule = paramFlowRule();
        rules.add(rule);
        ParamFlowRuleManager.loadRules(rules);
    }

    //配置热点参数限流
    private static ParamFlowRule paramFlowRule() {
        ParamFlowRule rule = new ParamFlowRule();
        rule.setResource("hotTest");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setLimitApp("default");
        rule.setCount(2);
        rule.setParamIdx(1);
        rule.setDurationInSec(10);
        List<ParamFlowItem> items = new ArrayList<>();
        items.add(nameParamItem("human", 100));
        items.add(nameParamItem("administrative", 100));
        rule.setParamFlowItemList(items);
        return rule;
    }

    //创建参数流控对象
    private static ParamFlowItem nameParamItem(String paramValue, int count) {
        ParamFlowItem item = new ParamFlowItem();
        item.setClassType(String.class.getName());
        item.setObject(String.valueOf(paramValue));
        item.setCount(count);
        return item;
    }



    public static void initAuthorityRule() {
        List<AuthorityRule> rules = new ArrayList<>();
        AuthorityRule rule = reqsourceRule();
        rules.add(rule);
        AuthorityRuleManager.loadRules(rules);
    }


    private static AuthorityRule reqsourceRule() {
        AuthorityRule rule = new AuthorityRule();
        rule.setResource("getDepartById");
        rule.setStrategy(RuleConstant.AUTHORITY_WHITE);
        rule.setLimitApp("serviceA,serviceB");
        return rule;
    }

    public static void initFlowRule() {
        List<FlowRule> flowRules = new ArrayList<>();
        FlowRule qpsRule = qpsFlowRule();
        flowRules.add(qpsRule);
        FlowRuleManager.loadRules(flowRules);
    }

    /**
     * qps流控
     * @return
     */
    private static FlowRule qpsFlowRule() {
        //创建流控规则对象
        FlowRule qpsRule = new FlowRule();
        //设置流控资源名称
        qpsRule.setResource("getDepartById");
        //设置流控规则【QPS和线程数】
        qpsRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //设置为线程隔离
        //qpsRule.setGrade(RuleConstant.FLOW_GRADE_THREAD);
        //设置QPS数为1
        qpsRule.setCount(2);
        //值为default，表示对请求来源不做限定
        qpsRule.setLimitApp("default");
        return qpsRule;
    }

    /**
     * 带流控模式的流控规则
     * @return
     */
    private static FlowRule qpsChainFlowRule() {
        FlowRule qpsRule = new FlowRule();
        qpsRule.setResource("qpsChainFlowRule");
        qpsRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        qpsRule.setCount(1);
        qpsRule.setStrategy(RuleConstant.STRATEGY_CHAIN);
        qpsRule.setRefResource("/provider/depart/list");
        qpsRule.setLimitApp("default");
        return qpsRule;
    }

    /**
     * Warm Up
     * @return
     */
    private static FlowRule qpsWarmUpFlowRule() {
        FlowRule qpsRule = new FlowRule();
        qpsRule.setResource("qpsWarmUpFlowRule");
        qpsRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        qpsRule.setLimitApp("default");
        qpsRule.setCount(20);
        qpsRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_WARM_UP);
        qpsRule.setWarmUpPeriodSec(5);
        return qpsRule;
    }

    /**
     * 排队等待
     * @return
     */
    private static FlowRule qpsQueueFlowRule() {
        FlowRule qpsRule = new FlowRule();
        qpsRule.setResource("qpsQueueFlowRule");
        qpsRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        qpsRule.setLimitApp("default");
        qpsRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER);
        qpsRule.setCount(20);
        qpsRule.setMaxQueueingTimeMs(20*1000);
        return qpsRule;
    }



    public static void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();
        // 获取定义的规则
        DegradeRule rule = slowRequestDegradeRule();
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }


    //慢调用比例 熔断降级规则
    public static DegradeRule slowRequestDegradeRule(){
        //创建一个降级规则实例
        DegradeRule rule = new DegradeRule();
        //设置配置熔断规则的资源名称
        rule.setResource("getDepartById");
        //熔断策略：慢调用比例、异常比例、异常数
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        //设置阈值：RT的时间，单位毫秒。若一个请求获取到响应的时间超出该值，则会将该请求统计 为“慢调用”
        rule.setCount(200);
        //熔断恢复时间窗口，单位秒
        rule.setTimeWindow(30);
        // 可触发熔断的最小请求数，默认是5个
        rule.setMinRequestAmount(1);
        // 设置发生慢调用的比例
        rule.setSlowRatioThreshold(0.5);
        return rule;
    }

    //异常比例 熔断降级规则
    private static DegradeRule errorRatioDegradeRule() {
        DegradeRule rule = new DegradeRule(); rule.setResource("getDepartById");
        // 指定熔断规则为 异常比例
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO);
        // 设置阈值：发生熔断的异常请求比例
        rule.setCount(0.5);
        rule.setTimeWindow(60);
        rule.setMinRequestAmount(5);
        return rule;
    }

    //异常数 熔断降级规则
    private static DegradeRule errorCountDegradeRule() {
        DegradeRule rule = new DegradeRule();
        rule.setResource("getDepartById");
        // 指定熔断规则为 异常数量
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        // 设置阈值：发生熔断的异常请求数量
        rule.setCount(5);
        rule.setTimeWindow(60);
        rule.setMinRequestAmount(5);
        return rule;
    }

}
