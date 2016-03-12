package com.itanbank.account.security;

import com.itanbank.account.common.MessageHelper;
import com.itanbank.account.domain.app.User;
import com.itanbank.account.domain.app.enums.YesOrNoEnum;
import com.itanbank.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author Administrator
 * @version $v: 1.0.0, $time:2015/10/13 14:44 Exp $
 */
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageHelper messageHelper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        try {
            Authentication auth = super.authenticate(authentication);

            // 重置登陆错误次数
            userService.resetFailNums(authentication.getName());
            return auth;
        } catch (BadCredentialsException e) {
            // 登陆出错+1
            User user = userService.updateFailNums(authentication.getName());
            if(user != null && user.getId() != null) {
                // 判断是帐号是否已经锁定
                if (YesOrNoEnum.getByCode(user.getLocked()).equals(YesOrNoEnum.Y)) {
                    int num = user.getLoginErrorCount();
                    throw new LockedException(messageHelper.getMessage("AbstractUserDetailsAuthenticationProvider.login.account.locked", new Object[]{num}));
                }
            }
            // 剩于机会
            int num = userService.getLoginSurplusNums(user);
            throw new BadCredentialsException(messageHelper.getMessage("AbstractUserDetailsAuthenticationProvider.login.password.error", new Object[]{num}));
        }
    }
}