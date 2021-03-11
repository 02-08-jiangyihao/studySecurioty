package com.jiangyihao.securtity.securtity;

import com.jiangyihao.utils.util.R;
import com.jiangyihao.utils.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>description: 退出处理器</p>
 * @ClassName: TokenLogoutHandler
 * @author: jiangyihao
 * @Date: 2021/3/3 22:11
 * @version: 1.0
 */
public class TokenLogoutHandler implements LogoutHandler {
    private TokenManager tokenManager;
    private RedisTemplate stringRedisTemplate;

    public TokenLogoutHandler(TokenManager tokenManager, RedisTemplate stringRedisTemplate) {
        this.tokenManager = tokenManager;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        //1 从Header中获取token
        //2 token不为空 ,移除token，从redis里面删除token
        String token = request.getHeader("token");
        if (null != token){
            /**
             * 因为没有存入token，所以不需要移除，符合流程写一下
             */
            tokenManager.removeToken(token);
            String userInfoFromToken = tokenManager.getUserInfoFromToken(token);
            /**
             * redis 删除
             */
            stringRedisTemplate.delete(userInfoFromToken);

        }
        ResponseUtil.out(response, R.ok());
    }
}
