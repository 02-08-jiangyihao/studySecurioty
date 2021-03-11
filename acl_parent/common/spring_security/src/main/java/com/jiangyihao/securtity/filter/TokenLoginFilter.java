package com.jiangyihao.securtity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiangyihao.securtity.entity.SecurityUser;
import com.jiangyihao.securtity.entity.User;
import com.jiangyihao.securtity.securtity.TokenManager;
import com.jiangyihao.utils.util.R;
import com.jiangyihao.utils.util.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * <p>description: 自定义认证过滤器</p>
 * @ClassName: TokenLoginFilter
 * @author: jiangyihao
 * @Date: 2021/3/3 22:24
 * @version: 1.0
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {


    private TokenManager tokenManager;

    private RedisTemplate redisTemplate;

    private AuthenticationManager authenticationManager;


    public TokenLoginFilter(AuthenticationManager authenticationManager,TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;

        this.setPostOnly(false);
        /**
         * 设置一个登录路径，是post提交
         */
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/acl/login","POST"));
    }

    /**
     * Description:获取表单提交用户名和密码
     * @author: jiangyihao
     * @Date: 2021/3/3 22:27
     * @return org.springframework.security.core.Authentication
     * @param request
     * @param response
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        //获取表单提交数据
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername().trim(),user.getPassword(),new ArrayList<>()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }


//        return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        //认证成功之后，得到用户信息
        SecurityUser principal = (SecurityUser)authResult.getPrincipal();
        //根据用户名生成token
        String teken = tokenManager.createTeken(principal.getCurrentUserInfo().getUsername());
        redisTemplate.opsForValue().set(principal.getCurrentUserInfo().getUsername(),principal.getPermissionValueList());
        /**
         * 返回token
         */
        ResponseUtil.out(response, R.ok().data("token",teken));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {

        //返回错误提示
        ResponseUtil.out(response, R.error());
//        super.unsuccessfulAuthentication(request, response, failed);
    }
}
