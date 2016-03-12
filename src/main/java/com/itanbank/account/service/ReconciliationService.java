package com.itanbank.account.service;

import com.itanbank.account.pay.model.query.DowmLoanFileRequest;
import com.itanbank.account.pay.model.query.DowmLoanFileResult;

import net.sf.json.JSONObject;

public interface ReconciliationService {
	
	/**
	 * 下载文件
	 * @param dowmLoanFileRequest
	 * @param path
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public DowmLoanFileResult downLoanFile(DowmLoanFileRequest dowmLoanFileRequest,String path,String fileName) throws Exception;
	
	/**
	 * 导入文件
	 * @param dowmLoanFileRequest
	 * @param path
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public JSONObject upLoanFile(DowmLoanFileRequest dowmLoanFileRequest,String path,String fileName) throws Exception;
	
}
