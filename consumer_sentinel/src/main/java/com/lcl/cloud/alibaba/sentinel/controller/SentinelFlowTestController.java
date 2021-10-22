package com.lcl.cloud.alibaba.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.lcl.cloud.alibaba.sentinel.bean.Depart;
import com.lcl.cloud.alibaba.sentinel.service.DepartSentinelFallBack;
import com.lcl.cloud.alibaba.sentinel.service.DepartService;
import com.lcl.cloud.alibaba.sentinel.service.DepartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 消费者Controller 基于Feign实现的接口调用
 */
@RestController
@RequestMapping("/sentinel/test")
public class SentinelFlowTestController {

    @Autowired
    private DepartServiceImpl departService;

    //跨服务根据列表查询
    @GetMapping("/list")

    public List<Depart> listHandle() {
        return departService.listAllDeparts();
    }

    //跨服务根据列表查询
    @GetMapping("/all")
    public List<Depart> allHandle(){
        return departService.listAllDeparts();
    }

}
