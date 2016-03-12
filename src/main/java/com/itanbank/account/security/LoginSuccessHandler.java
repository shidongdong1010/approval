package com.itanbank.account.security;

import com.itanbank.account.common.IpHelper;
import com.itanbank.account.domain.app.User;
import org.epbcommons.transformation.date.DateStyle;
import org.epbcommons.transformation.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆成功后处理
 * Created by dongdongshi on 16/1/12.
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException,
            ServletException {
        // 获得授权后可得到用户信息 可使用UserService进行数据库操作
        User userDetails = (User) authentication.getPrincipal();

        logger.info("用户 " + userDetails.getUsername() + " 登录");
        logger.info("IP :" + IpHelper.getIpAddress(request));
        logger.info("登陆时间 : " + DateUtil.DateToString(userDetails.getLastLoginTime(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        super.onAuthenticationSuccess(request, response, authentication);
    }
}