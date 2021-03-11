package com.jiangyihao.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * <p>description:解决跨越配置类 </p>
 * @ClassName: CorsConfig
 * @author: jiangyihao
 * @Date: 2021/3/4 11:23
 * @version: 1.0
 */
@Configuration
public class CorsConfig {
    /**
     * 解决跨越问题
     * @return
     */
    @Bean
    public CorsWebFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();
        /**允许访问的方法名,GET POST等*/
        config.addAllowedMethod("*");
        /**允许访问的客户端域名*/
        config.addAllowedOrigin("*");
        /**允许服务端访问的客户端请求头*/
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**",config);
        return new CorsWebFilter(source);
    }
}
