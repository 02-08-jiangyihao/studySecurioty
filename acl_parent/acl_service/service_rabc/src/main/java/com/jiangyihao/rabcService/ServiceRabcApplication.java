package com.jiangyihao.rabcService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName: com.jiangyihao.rabcService.ServiceRabcApplication
 * @Description:
 * @Author: jiangyihao
 * @Date:
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.jiangyihao")
@MapperScan("com.jiangyihao.rabcService.mapper")
public class ServiceRabcApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceRabcApplication.class, args);
    }
}
