package com.lcl.cloud.alibaba.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.lcl.cloud.alibaba.sentinel.bean.Depart;
import com.lcl.cloud.alibaba.sentinel.service.DepartSentinelFallBack;
import com.lcl.cloud.alibaba.sentinel.service.DepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 消费者Controller 基于Feign实现的接口调用
 */
@RestController
@RequestMapping("/sentinel/consumer/class/depart")
                                                                                                                                                                                                         public class SentinelClassController {

    @Autowired
    private DepartService departService;


    //跨服务根据id查询
    @GetMapping("/get/{id}")
    @SentinelResource(fallback = "getDepartByIdBack", fallbackClass = DepartSentinelFallBack.class)
    public Depart getHandle(@PathVariable("id") int id) {
        int i = 1/0;
        return departService.getDepartById(id);
    }

    //跨服务根据列表查询
    @GetMapping("/list")
    @SentinelResource(fallback = "listAllDepartsBack", fallbackClass = DepartSentinelFallBack.class)
    public List<Depart> listHandle() {
        return departService.listAllDeparts();
    }

}
