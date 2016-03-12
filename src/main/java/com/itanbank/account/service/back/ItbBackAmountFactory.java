package com.itanbank.account.service.back;

import com.itanbank.account.common.ApplicationHelper;
import com.itanbank.account.domain.web.ItbProjectInfo;
import com.itanbank.account.domain.web.enums.BusinessTypeEnum;
import com.itanbank.account.domain.web.enums.LoanUserTypeEnum;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by SDD on 2016/2/26.
 */
public class ItbBackAmountFactory {

    public static ItbBackAmountService getBackService(String businessType, String userType){
        // 得到业务类型
        BusinessTypeEnum businessTypeEnum = BusinessTypeEnum.getByCode(businessType);

        if(BusinessTypeEnum.REPAY_BACK.equals(businessTypeEnum)){// 还款后返款
            LoanUserTypeEnum loanUserTypeEnum = LoanUserTypeEnum.getByCode(userType);
            if(LoanUserTypeEnum.PERSON.equals(loanUserTypeEnum)){
                return (ItbBackAmountService) getApplicationContext().getBean("itbBackUserAmountServiceImpl");
            }
            return (ItbBackAmountService) getApplicationContext().getBean("itbBackCompanyAmountServiceImpl");
        }else if(BusinessTypeEnum.ADVANCE_BACK.equals(businessTypeEnum)){ // 偿付后返款
            return (ItbBackAmountService) getApplicationContext().getBean("itbBackAdvanceAmountServiceImpl");
        }else  if(BusinessTypeEnum.LATE_REPAY_SERVER_AMOUNT.equals(businessTypeEnum)){
            // 收取延迟还款服务费
            return (ItbBackAmountService) getApplicationContext().getBean("itbBackPlatformAmountServiceImpl");
        }
        return null;
    }

    private static ApplicationContext getApplicationContext(){
        return ApplicationHelper.getApplicationContext();
    }
}
