package com.itanbank.account.pay.model.check;

import com.itanbank.account.pay.model.BasicModel;

/**
 * 对账文件下载请求参数
 * 
 * 对账数据是每天早上7：00生成，请各商户于7：00以后获取昨天的对账记录。
 * @author wn
 *
 */
public class DownloadFileRequest {

	/** 接口名称*/
	private String service = "download_settle_file_p"; 
	
	/** 签名方式*/
	private String sign_type = BasicModel.SIGN_TYPE;
	/** 商户编号*/
	private String mer_id= BasicModel.MER_ID;
	/** 版本号*/
	private String version= BasicModel.VERSION;
	/** 对账日期*/
	private String settle_date_p2p = "20160125"; 
	/** 对账类型
	 * 01：充值对账文件
	 * 02：提现对账文件
	 * 03：标的对账文件（全量）
	 * 04：标的转账对账文件
	 * 05：转账对账文件
	 * 06：托管用户开户对账文件（增量）
	 */
	private String settle_type_p2p = "03";
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getMer_id() {
		return mer_id;
	}
	public void setMer_id(String mer_id) {
		this.mer_id = mer_id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSettle_date_p2p() {
		return settle_date_p2p;
	}
	public void setSettle_date_p2p(String settle_date_p2p) {
		this.settle_date_p2p = settle_date_p2p;
	}
	public String getSettle_type_p2p() {
		return settle_type_p2p;
	}
	public void setSettle_type_p2p(String settle_type_p2p) {
		this.settle_type_p2p = settle_type_p2p;
	}
	@Override
	public String toString() {
		return "DownloadFile [service=" + service + ", sign_type=" + sign_type + ", mer_id=" + mer_id + ", version="
				+ version + ", settle_date_p2p=" + settle_date_p2p + ", settle_type_p2p=" + settle_type_p2p + "]";
	} 
	
}
