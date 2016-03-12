package com.itanbank.account.pay.service;

import java.util.Map;

import com.itanbank.account.pay.model.query.*;
import org.apache.commons.beanutils.BeanUtils;
import org.epbcommons.httpclient.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.itanbank.account.pay.common.JsoupUtil;
import com.umpay.api.common.ReqData;
import com.umpay.api.paygate.v40.Mer2Plat_v40;

/**
 * Created by SDD on 2016/2/23.
 */
@Service
public class QueryService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 交易查询
     * @param request
     * @return
     * @throws Exception
     */
    public TransferResult transferSearch(TransferRequest request)throws Exception{
        logger.info("交易查询请求参数："+request.toString());
        Map<String, String> paramMap = BeanUtils.describe(request);
        ReqData reqData = Mer2Plat_v40.makeReqDataByGet(paramMap);
        String url = reqData.getUrl();
        logger.info("交易查询请求url："+url);

        String resultStr = HttpClientUtil.sendGetRequest(url, null);
        //String resultStr = HttpClientUtil.get(url);
        logger.info("交易查询结果："+resultStr);
        Map<String, String> resultMap = JsoupUtil.getResult(resultStr);
        TransferResult transferResult = new TransferResult();
        BeanUtils.populate(transferResult, resultMap);
        logger.info("交易查询结果解析："+transferResult.toString());
        return transferResult;
    }

    /**
     * 用户查询
     * @param user
     * @return
     * @throws Exception
     */
    public UserResult queryUser(UserRequest user) throws Exception{
        logger.info("用户查询请求参数："+user.toString());
        Map<String, String> paramMap = BeanUtils.describe(user);
        ReqData reqData = Mer2Plat_v40.makeReqDataByGet(paramMap);
        String url = reqData.getUrl();
        logger.info("用户查询请求url："+url);

        String resultStr = HttpClientUtil.sendGetRequest(url, null);
        //String resultStr = HttpClientUtil.get(url);
        logger.info("用户查询结果："+resultStr);
        Map<String, String> resultMap = JsoupUtil.getResult(resultStr);
        UserResult userResult = new UserResult();
        BeanUtils.populate(userResult, resultMap);
        logger.info("用户查询结果解析："+userResult.toString());

        return userResult;
    }
    /**
     * 标的查询
     * @param project
     * @return
     * @throws Exception
     */
    public ProjectResult project(ProjectRequest project) throws Exception{
		logger.info("标的查询请求参数："+project.toString());
		Map<String, String> paramMap = BeanUtils.describe(project);
		ReqData reqData = Mer2Plat_v40.makeReqDataByGet(paramMap);
		String url = reqData.getUrl();
		logger.info("标的查询请求url："+url);
		ProjectResult projectResult = new ProjectResult();
        String resultStr = HttpClientUtil.sendGetRequest(url, null);
		logger.info("标的查询结果："+resultStr);
    	Map<String, String> resultMap = JsoupUtil.getResult(resultStr);
    	BeanUtils.populate(projectResult, resultMap);
    	logger.info("标的查询结果解析："+projectResult.toString());
		return projectResult;
		
	} 
    
    /**
     * 账户流水查询
     * @param accountFlow
     * @return
     * @throws Exception
     */
    public  AccountFlowResult  accountFlow(AccountFlow accountFlow) throws Exception{
    	logger.info("标的查询请求参数："+accountFlow.toString());
		Map<String, String> paramMap = BeanUtils.describe(accountFlow);
		ReqData reqData = Mer2Plat_v40.makeReqDataByGet(paramMap);
		String url = reqData.getUrl();
		logger.info("标的查询请求url："+url);
		AccountFlowResult accountFlowResult = new AccountFlowResult();
        String resultStr = HttpClientUtil.sendGetRequest(url, null);
		logger.info("标的查询结果："+resultStr);
    	Map<String, String> resultMap = JsoupUtil.getResult(resultStr);
    	BeanUtils.populate(accountFlowResult, resultMap);
    	logger.info("标的查询结果解析："+accountFlowResult.toString());
		return accountFlowResult;
    }
}
