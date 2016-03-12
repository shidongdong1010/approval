package com.itanbank.account.pay.service;

import com.itanbank.account.pay.model.check.DownloadFileRequest;
import com.umpay.api.common.ReqData;
import com.umpay.api.paygate.v40.Mer2Plat_v40;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by SDD on 2016/2/23.
 */
@Service
public class DownAccountService {

    /**
     * 获取下载对账文件的URL
     * @param request
     * @return
     * @throws Exception
     */
    public String getDownloadCheckAccountFileUrl(DownloadFileRequest request) throws Exception{
        Map<String, String> paramMap = BeanUtils.describe(request);
        ReqData reqData = Mer2Plat_v40.makeReqDataByGet(paramMap);
        String url = reqData.getUrl();
        return url;
    }
}
