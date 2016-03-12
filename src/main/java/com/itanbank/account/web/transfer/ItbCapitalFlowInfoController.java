package com.itanbank.account.web.transfer;

import javax.servlet.http.HttpServletRequest;

import org.epbcommons.transformation.math.MathUtil;
import org.epbcommons.transformation.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itanbank.account.common.NumberFormatUtil;
import com.itanbank.account.common.page.JqGridPage;
import com.itanbank.account.common.page.PageUtils;
import com.itanbank.account.domain.web.ItbCapitalFlowInfo;
import com.itanbank.account.domain.web.enums.BusinessTypeEnum;
import com.itanbank.account.pay.enums.BusiTypeEnum;
import com.itanbank.account.pay.enums.ComAmtTypeEnum;
import com.itanbank.account.pay.enums.TranStateEnum;
import com.itanbank.account.pay.model.query.TransferRequest;
import com.itanbank.account.pay.model.query.TransferResult;
import com.itanbank.account.pay.service.QueryService;
import com.itanbank.account.service.ItbCapitalFlowService;

/**
 * Created by SDD on 2016/3/7.
 */
@Controller
public class ItbCapitalFlowInfoController {

    @Autowired
    private ItbCapitalFlowService itbCapitalFlowService;
    
    @Autowired
    private QueryService queryService;
    
    
    @RequestMapping("/transfer/capitalFlowList.html")
    public String capitalFlowList(){
        return "/transfer/capitalFlowList";
    }

    @RequestMapping("/transfer/capitalFlowList.json")
    @ResponseBody
    public JqGridPage capitalFlowList(ItbCapitalFlowInfo capitalFlowInfo, HttpServletRequest request){
        // Pageable 创建，提供页数，是当前显示条数
        Pageable pageable = PageUtils.getPageable(request);

        Page<ItbCapitalFlowInfo> pages = itbCapitalFlowService.findPage(capitalFlowInfo, pageable);
        return PageUtils.toJqGridPage(pages);
    }
    
    /**
	  * 查询第三方资金流水，对比本地资金流水
	  * @param orderNo
	  * @return
	  */
	 @RequestMapping("/transfer/queryCapitalFlowInfo.html")
	 public String  queryCapitalFlowInfo(HttpServletRequest request,String  orderNo)throws Exception{
		 //根据当前订单号，查询流水信息
		 ItbCapitalFlowInfo   itbCapitalFlowInfo = new ItbCapitalFlowInfo();
		 TransferResult transferResult = new TransferResult(); 
		 boolean  iseqAmount = true;
		 if(!StringUtil.isEmpty(orderNo)){
			 itbCapitalFlowInfo  = itbCapitalFlowService.findByOrderNo(orderNo);
			 //组装第三方查询模板
			 TransferRequest  transferRequest = new TransferRequest();
			 //分解订单号
			 transferRequest.setOrder_id(orderNo);//订单号
			 transferRequest.setBusi_type(BusinessTypeEnum.getByCode(itbCapitalFlowInfo.getBusinessType()).getQueryType());//查询类型
			 transferRequest.setMer_date(orderNo.substring(2, 10));//订单时间
			 //查询订单第三方数据
			 transferResult = queryService.transferSearch(transferRequest);
			 	if(MathUtil.equals(MathUtil.div(Double.parseDouble(transferResult.getAmount()), 100d, 2), itbCapitalFlowInfo.getAmount())){
			 		iseqAmount=true;
			 	}else{
			 		iseqAmount=false;
			 	}
		       if(null!=transferResult.getAmount() && !"".equals(transferResult.getAmount())){
		    	   transferResult.setAmount(NumberFormatUtil.getNumberTwoDecimal(String.valueOf(MathUtil.div(new Double(transferResult.getAmount()), 100d, 2))));
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
		 request.setAttribute("itbCapitalFlowInfo",itbCapitalFlowInfo);
		 request.setAttribute("transferResult",transferResult);
		 request.setAttribute("iseqAmount",iseqAmount);
		 return  "/transfer/queryCapitalFlowContrast";
	 }
}
