package com.lcl.cloud.alibaba.sentinel.config;

//import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 定义原始请求解析器，其用于从请求中获取来源标识
 */
@Component
public class DepartRequestOriginParser implements RequestOriginParser {
    /**
     * 该方法的返回值即为请求来源标识
     * @param request
     * @return
     */
    @Override
    public String parseOrigin(HttpServletRequest request) {
        // 本例的来源标识通过请求参数给出，
        // 这里获取的名称为source的请求参数就是来源名称
        String source = request.getParameter("source");
        // 指定默认来源名称为serviceA
        if (StringUtils.isEmpty(source)) {
            source = "serviceA";
        }
        // 返回的就是来源标识
        return source;
    }
}
