package com.wisdge.cloud.feign;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 文件传输接口
 */
@FeignClient(name = "cloud-fs", path = "/fs")
public interface FsServiceFeign {


    /**
     * 文件下载
     * @param map
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/get")
    Response get(@SpringQueryMap Map map);

}
