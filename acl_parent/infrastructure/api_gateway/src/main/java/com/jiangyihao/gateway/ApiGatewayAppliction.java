package com.jiangyihao.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: ApiGatewayAppliction
 * @Description:
 * @Author: jiangyihao
 * @Date:
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient //nacos注册服务
public class ApiGatewayAppliction {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayAppliction.class,args);
    }
}
