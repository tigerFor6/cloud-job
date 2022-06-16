package com.wisdge.cloud;

import com.wisdge.cloud.util.SpringApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.oas.annotations.EnableOpenApi;
import java.util.concurrent.CountDownLatch;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Controller
@EnableOpenApi
@Import(SpringApplicationUtils.class)
public class Application implements CommandLineRunner, DisposableBean {
    private final static CountDownLatch latch = new CountDownLatch(1);

    @Value("${cloud.app-name:cloud.job}")
    private String appName;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("{} ------>> 启动成功", appName);
    }

    @Override
    public void destroy() {
        latch.countDown();
        log.info("{} ------>> 关闭成功", appName);
    }

    @GetMapping("/")
    public String start() {
        return "redirect:/swagger-ui/index.html";
    }
}
