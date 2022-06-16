package com.wisdge.cloud.configurer;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * feign 配置文件
 * <p>
 * 将请求头中的参数，全部作为 feign 请求头参数传递
 */
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        //添加用户请求头，mock用户数据
        String userStr = "{\"userId\":\"161898830143422464\",\"client_id\":\"pc\",\"user_name\":\"dev\",\"jti\":\"jti_test_id\",\"authorities\":[\"SYSTEM\"]}";
        requestTemplate.header("user", userStr);
    }
}
