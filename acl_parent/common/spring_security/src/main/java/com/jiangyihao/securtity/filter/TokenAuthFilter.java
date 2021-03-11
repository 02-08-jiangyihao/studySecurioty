package com.jiangyihao.securtity.filter;

import com.jiangyihao.securtity.securtity.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>description: 自定义授权过滤器</p>
 * @ClassName: TokenAuthFilter
 * @author: jiangyihao
 * @Date: 2021/3/3 22:25
 * @version: 1.0
 */
public class TokenAuthFilter extends BasicAuthenticationFilter {

    private TokenManager tokenManager;

    private RedisTemplate redisTemplate;
    public TokenAuthFilter(AuthenticationManager authenticationManager,TokenManager tokenManager,RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.redisTemplate = redisTemplate;
        this.tokenManager = tokenManager;


    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        super.doFilterInternal(request, response, chain);
        //获取当前认证成功用户权限信息
        UsernamePasswordAuthenticationToken authRequest = getAuthentication(request);
        //判断如果有权限信息，放到权限上下文中
        if(authRequest != null) {
            SecurityContextHolder.getContext().setAuthentication(authRequest);
        }
        //放行
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        //从header获取token
        String token = request.getHeader("token");
        if (token != null){
            //从token获取用户名
            String userInfoFromToken = tokenManager.getUserInfoFromToken(token);
            //从redis获取对应权限列表
           List<String> permissionValueList= (List<String>)redisTemplate.opsForValue().get(userInfoFromToken);
           return new UsernamePasswordAuthenticationToken(userInfoFromToken,token,AuthorityUtils.commaSeparatedStringToAuthorityList(permissionValueList.toString()));

        }
        return null;
    }

}
