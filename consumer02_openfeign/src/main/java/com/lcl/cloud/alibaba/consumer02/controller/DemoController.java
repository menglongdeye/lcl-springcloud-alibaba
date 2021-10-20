package com.lcl.cloud.alibaba.consumer02.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoController {

    @RequestMapping("/header")
    public String headerHandler(HttpServletRequest request){
        String header = request.getHeader("demokey");
        header = header == null ? "header is null" : header;
        log.info("==============={}", header);
        return header;
    }


    @RequestMapping("/time")
    public String timeHandler(){
        return "time: " + System.currentTimeMillis();
    }
}
