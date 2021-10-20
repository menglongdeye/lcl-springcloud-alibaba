package com.lcl.cloud.alibaba.gateway.fallback;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayFallBackController {

    @GetMapping("/fb")
    public String fallback(){
        return "this is fallback";
    }

}
