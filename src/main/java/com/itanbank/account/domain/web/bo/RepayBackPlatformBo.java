package com.itanbank.account.domain.web.bo;

import com.itanbank.account.domain.web.ItbBackPlatformInfo;
import com.itanbank.account.domain.web.ItbRepayInfo;

public class RepayBackPlatformBo {
	
	private ItbRepayInfo repayInfo;
	private ItbBackPlatformInfo  backPlatformInfo;
	
	
	public ItbRepayInfo getRepayInfo() {
		return repayInfo;
	}
	public void setRepayInfo(ItbRepayInfo repayInfo) {
		this.repayInfo = repayInfo;
	}
	public ItbBackPlatformInfo getBackPlatformInfo() {
		return backPlatformInfo;
	}
	public void setBackPlatformInfo(ItbBackPlatformInfo backPlatformInfo) {
		this.backPlatformInfo = backPlatformInfo;
	}
	
}
