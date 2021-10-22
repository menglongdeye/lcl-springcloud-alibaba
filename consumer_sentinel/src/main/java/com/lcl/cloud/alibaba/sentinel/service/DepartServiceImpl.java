package com.lcl.cloud.alibaba.sentinel.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.lcl.cloud.alibaba.sentinel.bean.Depart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartServiceImpl {

    @SentinelResource(value = "listAllDeparts", fallback = "listAllDepartsBack")
    public List<Depart> listAllDeparts(){
        List<Depart> departs = new ArrayList<>();
        departs.add(Depart.builder().id(1234).name("========= listAllDeparts ==========").build());

        return departs;
    }

    public List<Depart> listAllDepartsBack(){
        List<Depart> departs = new ArrayList<>();
        departs.add(Depart.builder().name("sentinel method fall back").build());
        return departs;
    }
}
