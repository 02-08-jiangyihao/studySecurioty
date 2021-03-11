package com.jiangyihao.securtity.securtity;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;
/**
 * <p>description: token配置类</p>
 * @ClassName: TokenManager
 * @author: jiangyihao
 * @Date: 2021/3/3 22:11
 * @version: 1.0
 */
@Component
public class TokenManager {
    /**
     * token有效时长
     */
    private long tonkenEcpiration = 24*60*60*1000;
    /**
     * token密钥
     */

    /**
     * 被Configuration注解的类使用@Value会时效，因为spring会先扫描Configuration再扫描配置,
     * 本类在引用的类中使用构造注入，网上的@Bean PropertySourcesPlaceholderConfigurer 和 实现EnvironmentAware 都无效
     * 目前没有找到解决方法，只能写死
     */
    private static final String TOKEN_KEY="d8e523ff5430454da8233154d4c9bc1e";

    /**
     * 根据用户名生成token
     */
    public String createTeken(String userName){

        String toekn = Jwts.builder().setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + tonkenEcpiration))
                .signWith(SignatureAlgorithm.HS512, TOKEN_KEY).compressWith(CompressionCodecs.GZIP).compact();
        return toekn;
    }
    /**
     * 根据token字符串得到用户信息
     */
    public String getUserInfoFromToken(String token){
        String userInfo = Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody().getSubject();
        return userInfo;
    }

    /**
     * 删除token 客户端不保存token
     * @param token
     */
    public void removeToken(String token){

    }

}
