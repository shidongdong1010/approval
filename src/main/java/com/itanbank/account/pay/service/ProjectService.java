package com.itanbank.account.pay.service;

import java.util.Map;

import com.itanbank.account.pay.model.project.*;
import org.apache.commons.beanutils.BeanUtils;
import org.epbcommons.httpclient.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.itanbank.account.pay.common.JsoupUtil;
import com.umpay.api.common.ReqData;
import com.umpay.api.paygate.v40.Mer2Plat_v40;

/**
 * 标的操作服务
 * @author wn
 *
 */
@Service
public class ProjectService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 无密标的转入
	 * 注：可进行投标、债权购买、还款操作
	 * @param projectTransferNopwd
	 * @return
	 * @throws Exception
	 */
	public ProjectTransferNopwdResult transferNopwd(ProjectTransferNopwdRequest projectTransferNopwd) throws Exception{
		logger.info("无密标的转入请求参数："+projectTransferNopwd.toString());
		Map<String, String> paramMap = BeanUtils.describe(projectTransferNopwd);
		ReqData reqData = Mer2Plat_v40.makeReqDataByGet(paramMap);
		String url = reqData.getUrl();
		logger.info("无密标的转入请求url："+url);
		ProjectTransferNopwdResult projectTransferNopwdResult = new ProjectTransferNopwdResult();
		String resultStr = HttpClientUtil.sendGetRequest(url, null);
		logger.info("无密标的转入同步结果："+resultStr);
    	Map<String, String> resultMap = JsoupUtil.getResult(resultStr);
    	BeanUtils.populate(projectTransferNopwdResult, resultMap);
    	logger.info("无密标的转入同步结果解析："+projectTransferNopwdResult.toString());
		return projectTransferNopwdResult;
	}
	
	/**
	 * 标的更新
	 * @param projectUpdate
	 * @return
	 * @throws Exception
	 */
	public ProjectUpdateResult update(ProjectUpdateRequest projectUpdate) throws Exception{
		logger.info("标的更新请求参数："+projectUpdate.toString());
		Map<String, String> paramMap = BeanUtils.describe(projectUpdate);
		ReqData reqData = Mer2Plat_v40.makeReqDataByGet(paramMap);
		String url = reqData.getUrl();
		logger.info("标的更新请求url："+url);
		ProjectUpdateResult projectUpdateResult = new ProjectUpdateResult();
		String resultStr = HttpClientUtil.sendGetRequest(url,null);
		logger.info("标的更新同步结果："+resultStr);
    	Map<String, String> resultMap = JsoupUtil.getResult(resultStr);
    	BeanUtils.populate(projectUpdateResult, resultMap);
    	logger.info("标的更新同步结果解析："+projectUpdateResult.toString());
		return projectUpdateResult;
	}

	/**
	 * 标的转账
	 * @param request
	 * @return
	 */
	public ProjectTransferResult transfer(ProjectTransferRequest request) throws Exception{
		logger.info("标的转账请求参数：" + request.toString());
		Map<String, String> paramMap = BeanUtils.describe(request);
		ReqData reqData = Mer2Plat_v40.makeReqDataByGet(paramMap);
		String url = reqData.getUrl();
		logger.info("标的转账请求url：" + url);
		ProjectTransferResult projectTransferResult = new ProjectTransferResult();
		String resultStr = HttpClientUtil.sendGetRequest(url, null);
		logger.info("标的转账请求同步结果：" + resultStr);
		Map<String, String> resultMap = JsoupUtil.getResult(resultStr);
		BeanUtils.populate(projectTransferResult, resultMap);
		logger.info("标的转账请求结果解析：" + projectTransferResult.toString());
		return projectTransferResult;
	}

}
