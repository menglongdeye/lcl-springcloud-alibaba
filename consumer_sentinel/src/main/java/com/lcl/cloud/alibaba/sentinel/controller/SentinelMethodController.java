package com.lcl.cloud.alibaba.sentinel.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.lcl.cloud.alibaba.sentinel.bean.Depart;
import com.lcl.cloud.alibaba.sentinel.service.DepartSentinelFallBack;
import com.lcl.cloud.alibaba.sentinel.service.DepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消费者Controller 基于Feign实现的接口调用
 */
@RestController
@RequestMapping("/sentinel")
public class SentinelMethodController {

    @Autowired
    private DepartService departService;


    //跨服务根据id查询
    @GetMapping("/get/{id}")
    // 该注解表明当前方法是一个由Sentinel管理的资源，value属性用于指定该资源的名称
    @SentinelResource(value = "getDepartById", fallback = "getDepartByIdBack")
    public Depart getHandle(@PathVariable("id") int id) {
        //int i = 1/0;
        //return departService.getDepartById(id);
        return Depart.builder().id(123).name("consumer_sentinel").build();
    }


    //跨服务根据列表查询
    @GetMapping("/list")
    @SentinelResource(value = "listHandle", fallback = "listAllDepartsBack", fallbackClass = DepartSentinelFallBack.class)
    public List<Depart> listHandle() {
        return departService.listAllDeparts();
    }

    public Depart getDepartByIdBack( int id){
        return Depart.builder().id(id).name("sentinel method fall back").build();
    }


//    @GetMapping("/flow/test/{id}")
//    public Depart flowTest(@PathVariable("id") int id) {
//        Entry entry = null;
//        try {
//            entry = SphU.entry("qpsFlowRule");
//            return departService.getDepartById(id);
//        }catch (BlockException e){
//            return Depart.builder().id(id).name("exception flow=====").build();
//        }finally {
//            if(entry != null){
//                entry.exit();
//            }
//        }
//    }


    //跨服务根据id查询
    @GetMapping("/hot")
    // 该注解表明当前方法是一个由Sentinel管理的资源，value属性用于指定该资源的名称
    @SentinelResource(value = "hotTest", fallback = "hotTestBack")
    public Depart hotTest(int id, String name) {
        return Depart.builder().id(id).name(name).build();
    }

    public Depart hotTestBack(int id, String name){
        return Depart.builder().id(id).name("fallback==========" + name).build();
    }

}
