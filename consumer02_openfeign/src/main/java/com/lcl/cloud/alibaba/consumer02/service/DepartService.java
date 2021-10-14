package com.lcl.cloud.alibaba.consumer02.service;

import com.lcl.cloud.alibaba.consumer02.bean.Depart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FeignClient接口： 访问提供者服务，本质HttpURLConnection
 *
 * 简介：声明式REST客户端：Feign创建一个用JAX-RS或Spring MVC注释修饰的接口的实现类对象。
 *
 *    正向：url地址映射到SpringMVC的controller方法上
 *    逆向：SpringMVC注解逆向生成url
 *
 *  fallback属性： 指定服务降级处理类的字节码
 *    熔断器：服务和服务之间的异常处理机制
 *  configuration属性：指定Feign客户端配置类的字节码
 */
@FeignClient(value = "provider02Nacosconfig"
        //, fallback = DepartServiceImplFallBack.class
        //, configuration = LogConfig.class
        )//注解作用：声明当前为Feign客户端接口
@RequestMapping("/provider/depart")// 参数为要调用的提供者相应的uri，抽取所有方法的公有uri地址
public interface DepartService {//更加符合面向接口api调用习惯

    @PostMapping("/save")
    boolean saveDepart(@RequestBody Depart depart);
    @DeleteMapping("/del/{id}")
    boolean removeDepartById(@PathVariable("id") int id);
    @PutMapping("/update")
    boolean modifyDepart(@RequestBody Depart depart);
    @GetMapping("/get/{id}")
    Depart getDepartById(@PathVariable("id") int id);
    @GetMapping("/list")
    List<Depart> listAllDeparts();
}
