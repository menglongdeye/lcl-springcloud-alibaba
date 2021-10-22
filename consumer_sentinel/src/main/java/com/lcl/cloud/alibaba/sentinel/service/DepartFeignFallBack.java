package com.lcl.cloud.alibaba.sentinel.service;


import com.lcl.cloud.alibaba.sentinel.bean.Depart;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@RequestMapping("/feign/consumer/fallback/depart")
public class DepartFeignFallBack implements DepartService {
    @Override
    public boolean saveDepart(Depart depart) {
        return false;
    }

    @Override
    public boolean removeDepartById(int id) {
        return false;
    }

    @Override
    public boolean modifyDepart(Depart depart) {
        return false;
    }

    @Override//接口降级处理方法：在出现异常或者是找不到对应服务，就走这个服务，而不是给用户输出connection refuse
    public Depart getDepartById(int id) {
        return Depart.builder().name("feign fall back").build();
    }

    @Override
    public List<Depart> listAllDeparts() {
        return null;
    }
}
