package com.mrl.server.normal.service;

import com.mrl.common.entry.ServerConstant;
import com.mrl.server.normal.service.fallback.FeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * value：要访问的服务名称
 * contextId：服务别名，当定义多个 value相同的feign client时需要别名来区分
 * fallbackFactory：调用出异常时的回退方法
 * @author luc
 * @date 2020/7/2115:29
 */
@FeignClient(value = ServerConstant.Server_System,contextId = "iFeign",fallbackFactory = FeignServiceFallback.class)
public interface IFeignService {

    @GetMapping("feign")
    String feign(@RequestParam String name);
}
