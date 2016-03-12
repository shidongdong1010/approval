package com.itanbank.account.domain.web.bo;

import com.itanbank.account.domain.web.ItbBackUserInfo;
import com.itanbank.account.domain.web.ItbInvestInfo;

import java.io.Serializable;

/**
 * Created by SDD on 2016/3/2.
 */
public class InvestBackUserBo implements Serializable {
    /** 返款信息 **/
    private ItbBackUserInfo backUserInfo;
    /** 投资信息 **/
    private ItbInvestInfo investInfo;

    public ItbBackUserInfo getBackUserInfo() {
        return backUserInfo;
    }

    public void setBackUserInfo(ItbBackUserInfo backUserInfo) {
        this.backUserInfo = backUserInfo;
    }

    public ItbInvestInfo getInvestInfo() {
        return investInfo;
    }

    public void setInvestInfo(ItbInvestInfo investInfo) {
        this.investInfo = investInfo;
    }
}
