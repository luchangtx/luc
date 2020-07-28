package com.mrl.server.normal.Controller;

import com.mrl.server.normal.service.IFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luc
 * @date 2020/7/2115:50
 */
@RestController
@Slf4j
public class FeignController {
    @Autowired
    private IFeignService iFeignService;

    @GetMapping("feign")
    public String feign(String name) {
        log.info("Feign调用MRL-Server-System的/feign服务");
        return iFeignService.feign(name);
    }
}
