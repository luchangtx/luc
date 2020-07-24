package com.mrl.server.normal.service.fallback;

import com.alibaba.fastjson.JSONObject;
import com.mrl.common.entry.CResponse;
import com.mrl.common.entry.ServerConstant;
import com.mrl.server.normal.service.IFeignService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 调用失败回退方法，要让回退生效，需要在yml文件增加配置开启回退
 * @author luc
 * @date 2020/7/2115:32
 */
@Component
@Slf4j
public class FeignServiceFallback implements FallbackFactory<IFeignService> {
    @Override
    public IFeignService create(Throwable throwable) {
        return name -> {
            String title="调用"+ ServerConstant.Server_System +"服务出错";
            log.error(title,throwable);
            return JSONObject.toJSONString(new CResponse().message(title));
        };
    }
}
