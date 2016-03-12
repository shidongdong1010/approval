package com.itanbank.account.pay.service;

import com.itanbank.account.pay.model.pay.RechargeEnterpriseRequest;
import com.itanbank.account.pay.model.pay.WithdrawEnterpriseRequest;
import com.umpay.api.common.ReqData;
import com.umpay.api.paygate.v40.Mer2Plat_v40;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by SDD on 2016/2/24.
 */
@Service
public class PayService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获得企业充值申请的URL
     *
     * @param request
     * @return
     * @throws Exception
     */
    public String getRechargeEnterpriseUrl(RechargeEnterpriseRequest request) throws Exception {
        logger.info("企业客户充值请求参数:" + request.toString());
        Map<String, String> paramMap = BeanUtils.describe(request);
        ReqData reqData = Mer2Plat_v40.makeReqDataByGet(paramMap);
        String url = reqData.getUrl();
        logger.info("企业客户充值请求url:" + url);
        return url;
    }

    /**
     * 获得企业提现申请的URL
     *
     * @param request
     * @return
     * @throws Exception
     */
    public String getWithdrawEnterpriseUrl(WithdrawEnterpriseRequest request) throws Exception {
        logger.info("企业客户提现请求参数:" + request.toString());
        Map<String, String> paramMap = BeanUtils.describe(request);
        ReqData reqData = Mer2Plat_v40.makeReqDataByGet(paramMap);
        String url = reqData.getUrl();
        logger.info("企业客户提现请求url:" + url);
        return url;
    }
}
