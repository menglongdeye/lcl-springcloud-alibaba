package com.lcl.cloud.alibaba.consumer02.controller;

import com.lcl.cloud.alibaba.consumer02.bean.Depart;
import com.lcl.cloud.alibaba.consumer02.service.DepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消费者Controller 基于Feign实现的接口调用
 */
@RestController
@RequestMapping("/feign/consumer/depart")
public class DepartFeignController {

    @Autowired
    private DepartService departService;

    //跨服务新增
    @PostMapping("/save")
    public boolean saveHandle(@RequestBody Depart depart) {
        return departService.saveDepart(depart);
    }

    //跨服务根据id删除
    @DeleteMapping("/del/{id}")
    public void deleteHandle(@PathVariable("id") int id) {
        departService.removeDepartById(id);
    }
    //跨服务修改
    @PutMapping("/update")
    public void updateHandle(@RequestBody Depart depart) {
        departService.saveDepart(depart);
    }
    //跨服务根据id查询
    @GetMapping("/get/{id}")
    public Depart getHandle(@PathVariable("id") int id) {
        return departService.getDepartById(id);
    }

    //跨服务根据列表查询
    @GetMapping("/list")
    public List<Depart> listHandle() {
        return departService.listAllDeparts();
    }
}
