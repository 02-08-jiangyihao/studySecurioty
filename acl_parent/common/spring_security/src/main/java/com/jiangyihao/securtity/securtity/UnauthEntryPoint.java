package com.jiangyihao.securtity.securtity;

import com.jiangyihao.utils.util.R;
import com.jiangyihao.utils.util.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>description: 未授权统一处理类</p>
 * @ClassName: UnauthEntryPoint
 * @author: jiangyihao
 * @Date: 2021/3/3 22:19
 * @version: 1.0
 */
public class UnauthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResponseUtil.out(httpServletResponse, R.error());
    }
}
