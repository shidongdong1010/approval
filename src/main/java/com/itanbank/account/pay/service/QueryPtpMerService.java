package com.itanbank.account.pay.service;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.epbcommons.httpclient.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.itanbank.account.pay.common.JsoupUtil;
import com.itanbank.account.pay.model.query.EnterpriseRequest;
import com.itanbank.account.pay.model.query.EnterpriseResult;
import com.umpay.api.common.ReqData;
import com.umpay.api.paygate.v40.Mer2Plat_v40;

@Service
public class QueryPtpMerService {
private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public EnterpriseResult enterprise(EnterpriseRequest enterprise) throws Exception{
		
		 logger.info("企业商户查询请求参数："+enterprise.toString());
	        Map<String, String> paramMap = BeanUtils.describe(enterprise);
	        ReqData reqData = Mer2Plat_v40.makeReqDataByGet(paramMap);
	        String url = reqData.getUrl();
	        logger.info("企业商户查询请求url："+url);
	        
	        String resultStr = HttpClientUtil.sendGetRequest(url, null);
	        logger.info("企业商户查询结果："+resultStr);
	        
	        Map<String, String> resultMap = JsoupUtil.getResult(resultStr);
	        EnterpriseResult enterpriseResult = new EnterpriseResult();
	        BeanUtils.populate(enterpriseResult, resultMap);
	        logger.info("企业商户查询请求："+enterpriseResult.toString());
	        
		return enterpriseResult;
	}
}
