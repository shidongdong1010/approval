package com.itanbank.account.domain.web.bo;

import com.itanbank.account.domain.web.ItbBackAdvanceInfo;
import com.itanbank.account.domain.web.ItbRepayInfo;

/**
 * Created by SDD on 2016/3/2.
 */
public class RepayBackAdvanceBo {
    private ItbRepayInfo repayInfo;

    private ItbBackAdvanceInfo backAdvanceInfo;

    public ItbRepayInfo getRepayInfo() {
        return repayInfo;
    }

    public void setRepayInfo(ItbRepayInfo repayInfo) {
        this.repayInfo = repayInfo;
    }

    public ItbBackAdvanceInfo getBackAdvanceInfo() {
        return backAdvanceInfo;
    }

    public void setBackAdvanceInfo(ItbBackAdvanceInfo backAdvanceInfo) {
        this.backAdvanceInfo = backAdvanceInfo;
    }
}
