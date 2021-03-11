package com.jiangyihao.securtity.securtity;

import com.jiangyihao.utils.util.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <p>description: 使用MD5加密</p>
 * @ClassName: DefaultPasswordEncoder
 * @author: jiangyihao
 * @Date: 2021/3/3 15:46
 * @version: 1.0
 */
@Component
public class DefaultPasswordEncoder implements PasswordEncoder {


    public DefaultPasswordEncoder() {
        this(-1);
    }
    public DefaultPasswordEncoder(int strength) {

    }
    /**
     * Description: 进行MD5加密
     * @author: jiangyihao
     * @Date: 2021/3/3 16:03
     * @return java.lang.String
     * @param charSequence
     * @throws
     */
    @Override
    public String encode(CharSequence charSequence) {

        return MD5.encrypt(charSequence.toString());
    }
    /**
     * Description: 进行密码比较
     * @author: jiangyihao
     * @Date: 2021/3/3 16:04
     * @return boolean
     * @param charSequence
     * @param encodePassword
     */
    @Override
    public boolean matches(CharSequence charSequence, String encodePassword) {
        return encodePassword.equals(MD5.encrypt(charSequence.toString()));
    }
}
