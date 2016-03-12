package com.itanbank.account.service;

import com.itanbank.account.domain.account.CsUserBak;

public interface BackupService {
	/**
	 * 备份用户信息
	 * @param csUserBak
	 */
	public void saveCsUserBak(CsUserBak csUserBak);
	
	/**
	 * 备份用户信息
	 * @param batchNo
	 */
	public void backUpUserInfo(String batchNo) throws Exception;
	
	/**
	 * 备份标的信息
	 * @param batchNo
	 */
	public void backUpProjectInfo(String batchNo) throws Exception;
	
	/**
	 * 备份公司账户信息
	 * @param batchNo
	 */
	public void backUpCompanyInfo(String batchNo) throws Exception;
	
	/**
	 * 备份资金流水信息
	 * @param batchNo
	 */
	public void backUpCapitalInfo(String batchNo,String date) throws Exception;
	
}
