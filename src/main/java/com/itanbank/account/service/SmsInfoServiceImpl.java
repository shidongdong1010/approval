package com.itanbank.account.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itanbank.account.domain.web.ItbSmsInfo;
import com.itanbank.account.pay.enums.SmsStatusEnum;
import com.itanbank.account.repository.web.ItbSmsInfoRepository;

@Service
public class SmsInfoServiceImpl implements SmsInfoService {

    @Autowired
    private ItbSmsInfoRepository itbSmsInfoRepository;


    @Override
    public boolean saveSms(String mobile, String msg, String businessType, Long userId, String ip) throws Exception {
        ItbSmsInfo smsInfo = new ItbSmsInfo();
        smsInfo.setUserId(userId);
        smsInfo.setBusinessType(businessType);
        smsInfo.setMobile(mobile);
        smsInfo.setMsg(msg);
        smsInfo.setIp(ip);
        smsInfo.setCreateTime(new Date());
        smsInfo.setStatus(SmsStatusEnum.SEND_WAIT.getCode());
        itbSmsInfoRepository.save(smsInfo);
        return true;
    }

}
