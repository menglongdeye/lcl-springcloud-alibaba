package com.lcl.cloud.alibaba.sentinel.service;


import com.lcl.cloud.alibaba.sentinel.bean.Depart;

import java.util.List;

public class DepartSentinelFallBack {


    public static Depart getDepartByIdBack(int id, Throwable e) {
        return Depart.builder().id(id).name("sentinel class fall back======" + e.getMessage()).build();
    }

    public static List<Depart> listAllDepartsBack() {
        return null;
    }
}
