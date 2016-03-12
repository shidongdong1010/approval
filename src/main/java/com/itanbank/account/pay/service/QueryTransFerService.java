package com.itanbank.account.pay.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.epbcommons.httpclient.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.itanbank.account.pay.common.JsoupUtil;
import com.itanbank.account.pay.model.query.TransferRequest;
import com.itanbank.account.pay.model.query.TransferResult;
import com.umpay.api.common.ReqData;
import com.umpay.api.paygate.v40.Mer2Plat_v40;

/**
 * Created by wp on 2016/2/25.
 */
@Service
public class QueryTransFerService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public TransferResult  transfer(TransferRequest transferRequest) throws Exception{
		
		 logger.info("交易查询请求参数："+transferRequest.toString());
	        Map<String, String> paramMap = BeanUtils.describe(transferRequest);
	        ReqData reqData = Mer2Plat_v40.makeReqDataByGet(paramMap);
	        String url = reqData.getUrl();
	        logger.info("交易查询请求url："+url);
	        
	        String resultStr = HttpClientUtil.sendGetRequest(url, null);
	        logger.info("交易查询结果："+resultStr);
	        
	        Map<String, String> resultMap = JsoupUtil.getResult(resultStr);
	        TransferResult transferResult = new TransferResult();
	        BeanUtils.populate(transferResult, resultMap);
	        logger.info("交易查询请求："+transferResult.toString());
	        
		return transferResult;
	}
}
