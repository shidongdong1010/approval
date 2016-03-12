package com.itanbank.account.service;

public interface SmsInfoService {
	
	/**
	* 发送短信
     * @param mobile 手机
     * @param msg 短信内容
     * @param businessType 业务类型
     * @param userId 用户ID
     * @param ip IP
     * @return
	 */
	public boolean saveSms(String mobile, String msg, String businessType, Long userId, String ip) throws Exception;
}
