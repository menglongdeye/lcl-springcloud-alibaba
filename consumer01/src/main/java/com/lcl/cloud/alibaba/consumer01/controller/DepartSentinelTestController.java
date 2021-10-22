package com.lcl.cloud.alibaba.consumer01.controller;

import com.lcl.cloud.alibaba.consumer01.bean.Depart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 消费者Controller
 */
@RestController
@RequestMapping("/sentinel/test")
public class DepartSentinelTestController {
    //注入RestTemplate模板对象
    @Autowired
    private RestTemplate restTemplate;

    //配置请求地址
    private static final String SERVICE_PROVIDER = "http://consumer-sentinel-depart";


    //跨服务根据id查询
    @GetMapping("/get/{id}")
    public List<Depart> getHandle(@PathVariable("id") int id) {
        String url = SERVICE_PROVIDER + "/sentinel/consumer/method/depart/get/" + id;
        List<Depart> departs = new ArrayList<>();
        for (int i=0; i<10; i++){
            Depart depart = restTemplate.getForObject(url, Depart.class);
            departs.add(depart);
        }
        return departs;
    }


    //跨服务根据id查询
    @GetMapping("/list")
    public List<Depart> getList() {
        String url = SERVICE_PROVIDER + "/sentinel/test/list";
        List<Depart> departs = new ArrayList<>();
        for (int i=0; i<10; i++){
            List<Depart> depart =  restTemplate.getForObject(url, List.class);
            departs.addAll(depart);
        }
        return departs;
    }

    //跨服务根据id查询
    @GetMapping("/all")
    public List<Depart> getAll() {
        String url = SERVICE_PROVIDER + "/sentinel/test/all";
        List<Depart> departs = new ArrayList<>();
        for (int i=0; i<10; i++){
            List<Depart> depart = restTemplate.getForObject(url, List.class);
            departs.addAll(depart);
        }
        return departs;
    }
}
