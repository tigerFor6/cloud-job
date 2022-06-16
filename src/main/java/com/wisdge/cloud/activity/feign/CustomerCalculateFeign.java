package com.wisdge.cloud.activity.feign;

import com.wisdge.cloud.dto.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 客户计算接口
 */
@FeignClient(name = "cloud-calculate",  url = "${service-api.cloud-calculate:}")
public interface CustomerCalculateFeign {

    @PostMapping("/calculate/customer/synCustomerInfoJob")
    ApiResult synCustomerInfoJob();

    @PostMapping("/calculate/customer/updateBaseInfo")
    ApiResult updateBaseInfo(@RequestParam("customerId") String customerId);

    @PostMapping("/calculate/customer/updateCustPositions")
    ApiResult updateCustPositions();

    @PostMapping("/calculate/customer/updateCustPositionCounts")
    ApiResult updateCustPositionCounts();

    @PostMapping("/calculate//customer/mot/updateTradeInfo")
    ApiResult updateTradeInfo();

    @PostMapping("/calculate/customer/mot/task")
    ApiResult task(@RequestParam("taskId") String taskId);

    @PostMapping("/calculate/customer/updateFundTypeCountJob")
    ApiResult updateFundTypeCountJob();

    @PostMapping("/calculate/customer/updateCustomerLoseJob")
    ApiResult updateCustomerLoseJob();
}
