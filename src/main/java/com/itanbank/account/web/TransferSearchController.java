package com.itanbank.account.web;

import com.itanbank.account.common.CommonUtil;
import com.itanbank.account.common.NumberFormatUtil;
import com.itanbank.account.common.page.JqGridPage;
import com.itanbank.account.common.page.PageUtils;
import com.itanbank.account.pay.enums.AccountStateEnum;
import com.itanbank.account.pay.enums.AmtTypeEnum;
import com.itanbank.account.pay.enums.BusiTypeEnum;
import com.itanbank.account.pay.enums.ComAmtTypeEnum;
import com.itanbank.account.pay.enums.DcMarkEnum;
import com.itanbank.account.pay.enums.ExecuetResultCode;
import com.itanbank.account.pay.enums.ProjectAccountStateEnum;
import com.itanbank.account.pay.enums.ProjectStateEnum;
import com.itanbank.account.pay.enums.TranStateEnum;
import com.itanbank.account.pay.enums.TransStateEnum;
import com.itanbank.account.pay.enums.TransTypeEnum;
import com.itanbank.account.pay.model.query.AccountFlow;
import com.itanbank.account.pay.model.query.AccountFlowDetail;
import com.itanbank.account.pay.model.query.AccountFlowResult;
import com.itanbank.account.pay.model.query.ProjectRequest;
import com.itanbank.account.pay.model.query.ProjectResult;
import com.itanbank.account.pay.model.query.TransferRequest;
import com.itanbank.account.pay.model.query.TransferResult;
import com.itanbank.account.pay.model.query.UserRequest;
import com.itanbank.account.pay.model.query.UserResult;
import com.itanbank.account.pay.service.QueryService;
import com.umpay.api.common.ReqData;
import com.umpay.api.paygate.v40.Mer2Plat_v40;
import com.umpay.api.util.SignUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.epbcommons.transformation.math.MathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 交易查询
 * Created by SDD on 2016/3/6.
 */
@Controller
public class TransferSearchController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QueryService queryService;

    @RequestMapping("/search/transferSearchList.html")
    public String transferSearchList(){
        return "/search/transferSearchList";
    }

    @RequestMapping("/search/transferSearchList.json")
    @ResponseBody
    public TransferResult transferSearchList(HttpServletRequest request, TransferRequest transferRequest) throws Exception{
       String order_id = transferRequest.getOrder_id();
       if(null!=order_id && !"".equals(order_id)){
    	   transferRequest.setMer_date(order_id.substring(2, 10));
       }
       TransferResult transferResult = queryService.transferSearch(transferRequest);
       if(ExecuetResultCode.SCUESS.getCode().equals(transferResult.getRet_code())){
    	   if(null!=transferResult.getAmount() && !"".equals(transferResult.getAmount())){
        	   transferResult.setAmount(NumberFormatUtil.getNumberTwoDecimal(String.valueOf(MathUtil.div(new Double(transferResult.getAmount()), 100d, 2)))+"元");
           }else{
        	   transferResult.setAmount("0元");
           }
           if(null!=transferResult.getCom_amt() && !"".equals(transferResult.getCom_amt())){
        	   transferResult.setCom_amt(NumberFormatUtil.getNumberTwoDecimal(String.valueOf(MathUtil.div(new Double(transferResult.getCom_amt()), 100d, 2)))+"元");
           }else{
        	   transferResult.setCom_amt("0元");
           }
           if(null!=transferResult.getOrig_amt() && !"".equals(transferResult.getOrig_amt())){
        	   transferResult.setOrig_amt(NumberFormatUtil.getNumberTwoDecimal(String.valueOf(MathUtil.div(new Double(transferResult.getOrig_amt()), 100d, 2)))+"元");
           }else{
        	   transferResult.setOrig_amt("0元");
           }
           if(null!=transferResult.getBusi_type() && !"".equals(transferResult.getBusi_type())){
        	   if(null!=transferResult.getTran_state()&& !"".equals(transferResult.getTran_state())){
            	   transferResult.setTran_state(TranStateEnum.getByCode(transferResult.getBusi_type()+transferResult.getTran_state()).getDesc());
               }else{
            	   transferResult.setTran_state("未知");
               }
        	   transferResult.setBusi_type(BusiTypeEnum.getByCode(transferResult.getBusi_type()).getDesc());
           }else{
        	   transferResult.setBusi_type("未知");
           }
           if(null!=transferResult.getCom_amt_type()&& !"".equals(transferResult.getCom_amt_type())){
        	   transferResult.setCom_amt_type(ComAmtTypeEnum.getByCode(transferResult.getCom_amt_type()).getDesc());
           }else{
        	   transferResult.setCom_amt_type("未知");
           }
       }
       return transferResult;
    }

    @RequestMapping("/search/transferSearchGetUrl.json")
    @ResponseBody
    public String getUrl(TransferResult transferResult) throws Exception {
        StringBuilder url = new StringBuilder();
        url.append("http://itanbank-account.ngrok.cc/pay/project/transferNotify/charge.html?");
        url.append("sign=").append(URLEncoder.encode(transferResult.getSign(), "utf-8"));
        url.append("&trade_no=").append(transferResult.getTrade_no());
        url.append("&ret_code=").append(transferResult.getRet_code());
        url.append("&mer_date=").append(transferResult.getMer_date());
        url.append("&mer_check_date=").append(transferResult.getMer_id());
        url.append("&mer_id=").append(transferResult.getMer_id());
        url.append("&sign_type=").append(transferResult.getSign_type());
        url.append("&ret_msg=").append(URLEncoder.encode(transferResult.getRet_msg(), "utf-8"));
        url.append("&service=").append("project_transfer");
        url.append("&order_id=").append(transferResult.getOrder_id());
        url.append("&version=").append(transferResult.getVersion());

        logger.info("根据交易查询结果生成2url:{}", url);
        return url.toString();
    }
    
    @RequestMapping("/search/userSearchList.html")
    public String userSearchList(){
        return "/search/userSearchList";
    }
    
    @RequestMapping("/search/userSearchList.json")
    @ResponseBody
    public UserResult userSearchList(HttpServletRequest request, UserRequest userRequest) throws Exception{
    	UserResult userResult = queryService.queryUser(userRequest);
    	if(ExecuetResultCode.SCUESS.getCode().equals(userResult.getRet_code())){
    		if(!StringUtils.isBlank(userResult.getAccount_state())){
        		userResult.setAccount_state(AccountStateEnum.getByCode(userResult.getAccount_state()).getDesc());
        	}
        	userResult.setIdentity_code(CommonUtil.dealIdCard(userResult.getIdentity_code()));
        	userResult.setCard_id(CommonUtil.dealBankNo(userResult.getCard_id()));
        	if(!StringUtils.isBlank(userResult.getBalance())){
        		userResult.setBalance(NumberFormatUtil.getNumberTwoDecimal(String.valueOf(MathUtil.div(new Double(userResult.getBalance()), 100d, 2)))+"元");
        	}else{
        		userResult.setBalance("0元");
        	}
    	}
    	return userResult;
    }
    
    @RequestMapping("/search/projectSearchList.html")
    public String projectSearchList(){
        return "/search/projectSearchList";
    }
    
    @RequestMapping("/search/projectSearchList.json")
    @ResponseBody
    public ProjectResult projectSearchList(HttpServletRequest request, ProjectRequest projectRequest) throws Exception{
    	ProjectResult projectResult = queryService.project(projectRequest);
    	if(ExecuetResultCode.SCUESS.getCode().equals(projectResult.getRet_code())){
    		if(!StringUtils.isBlank(projectResult.getProject_state())){
        		projectResult.setProject_state(ProjectStateEnum.getByCode(projectResult.getProject_state()).getDesc());
        	}
        	if(!StringUtils.isBlank(projectResult.getProject_account_state())){
        		projectResult.setProject_account_state(ProjectAccountStateEnum.getByCode(projectResult.getProject_account_state()).getDesc());
        	}
        	if(!StringUtils.isBlank(projectResult.getBalance())){
        		projectResult.setBalance(NumberFormatUtil.getNumberTwoDecimal(String.valueOf(MathUtil.div(new Double(projectResult.getBalance()), 100d, 2)))+"元");
        	}else{
        		projectResult.setBalance("0元");
        	}
    	}
    	return projectResult;
    }
    
    @RequestMapping("/search/accountFlowSearchList.html")
    public String accountFlowSearchList(){
        return "/search/accountFlowList";
    }
    
    @RequestMapping("/search/accountFlowSearchList.json")
    @ResponseBody
    public JqGridPage accountFlowSearchList(HttpServletRequest request, AccountFlow accountFlow) throws Exception{
    	// Pageable 创建，提供页数，是当前显示条数
        Pageable pageable = PageUtils.getPageable(request);
        JqGridPage jqGridPage = new JqGridPage();
        String logo = request.getParameter("logo");
    	if(null==logo || "".equals(logo)){
    		return jqGridPage;
    	}
    	int currpage = pageable.getPageNumber();
    	accountFlow.setPage_num(String.valueOf(currpage+1));
    	AccountFlowResult accountFlowResult = queryService.accountFlow(accountFlow);
    	if(null!=accountFlowResult && ExecuetResultCode.SCUESS.getCode().equals(accountFlowResult.getRet_code())){
    		String detailStr = accountFlowResult.getTrans_detail();
    		List<AccountFlowDetail> details = new ArrayList<AccountFlowDetail>();
    		if(!StringUtils.isBlank(detailStr)){
    			String[] detailArr = detailStr.split("\\|");
    			for(int i=0;i<detailArr.length;i++){
    				String detailObj = detailArr[i];
    				String[] detail = detailObj.split(",");
    				AccountFlowDetail accountFlowDetail = new AccountFlowDetail();
    				for(int j=0;j<detail.length;j++){
    					String accountFlowObj = detail[j];
    					String[] objArr = accountFlowObj.split("=");
    					if("acc_check_date".equals(objArr[0])){
    						accountFlowDetail.setAcc_check_date(objArr[1]);
    					}else if("amount".equals(objArr[0])){
    						accountFlowDetail.setAmount(NumberFormatUtil.getNumberTwoDecimal(String.valueOf(MathUtil.div(new Double(objArr[1]), 100d, 2)))+"元");
    					}else if("amt_type".equals(objArr[0])){
    						accountFlowDetail.setAmt_type(AmtTypeEnum.getByCode(objArr[1]).getDesc());
    					}else if("balance".equals(objArr[0])){
    						accountFlowDetail.setBalance(NumberFormatUtil.getNumberTwoDecimal(String.valueOf(MathUtil.div(new Double(objArr[1]), 100d, 2)))+"元");
    					}else if("dc_mark".equals(objArr[0])){
    						accountFlowDetail.setDc_mark(DcMarkEnum.getByCode(objArr[1]).getDesc());
    					}else if("order_date".equals(objArr[0])){
    						accountFlowDetail.setOrder_date(objArr[1]);
    					}else if("order_id".equals(objArr[0])){
    						accountFlowDetail.setOrder_id(objArr[1]);
    					}else if("trans_date".equals(objArr[0])){
    						accountFlowDetail.setTrans_date(objArr[1]);
    					}else if("trans_state".equals(objArr[0])){
    						accountFlowDetail.setTrans_state(TransStateEnum.getByCode(objArr[1]).getDesc());
    					}else if("trans_time".equals(objArr[0])){
    						accountFlowDetail.setTrans_time(objArr[1]);
    					}else if("trans_type".equals(objArr[0])){
    						accountFlowDetail.setTrans_type(TransTypeEnum.getByCode(objArr[1]).getDesc());
    					}
    				}
    				details.add(accountFlowDetail);
    			}
    		}
    		String totalNum = accountFlowResult.getTotal_num();
    		int total = Integer.parseInt(totalNum);
    		int pageTotal = total%10==0?total/10:total/10+1;
    		jqGridPage.setPage(currpage);
    		jqGridPage.setTotal(pageTotal);
    		jqGridPage.setRecords(total);
    		jqGridPage.setRows(details);
    	}
    	return jqGridPage;
    }
    
}
