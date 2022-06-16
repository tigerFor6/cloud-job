package com.wisdge.cloud.activity.feign;

import com.wisdge.cloud.dto.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调度任务接口
 */
@FeignClient(name = "cloud-portal", url = "${service-api.zinger-portal:}")
public interface TgCustomerGroupFeign {

    @GetMapping("/tag/customergroup/getCustomerList")
    ApiResult getCustomerList(@RequestParam("customerGroupId") String customerGroupId);

    @GetMapping("/event/task/findCountByStatusAndUserId")
    ApiResult findCountByStatusAndUserId(@RequestParam(value = "createBy") String createBy);

    /**
     * 发送企微消息
     *
     * @param phone   手机号
     * @param content 发送内容
     * @return
     */
    @GetMapping("/event/task/sendWxActivityNotice")
    ApiResult sendWxActivityNotice(@RequestParam(value = "phone") String phone, @RequestParam(value = "content") String content);
}
