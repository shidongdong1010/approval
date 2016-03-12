package com.itanbank.account.pay.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 第三方支付验签
 * Created by SDD on 2016/2/25.
 */
public class PaySignUtil {

    private static Logger logger = LoggerFactory.getLogger(PaySignUtil.class);

    /**
     * 验签
     * @param request
     * @return
     */
    public static boolean verify(HttpServletRequest request){
        boolean verify = false;
        try{
            com.umpay.api.paygate.v40.Plat2Mer_v40.getPlatNotifyData(request);
            verify = true;
        }catch(Exception e){
            logger.error("验证签名发生异常", e);
            verify = false;
        }
        return verify;
    }
}
