package com.itanbank.account.web;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.epbcommons.transformation.math.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itanbank.account.common.JSONUtils;
import com.itanbank.account.common.NumberFormatUtil;
import com.itanbank.account.domain.account.CsProjectDetail;
import com.itanbank.account.domain.account.CsProjectTransferDetail;
import com.itanbank.account.domain.account.CsRechargeDetail;
import com.itanbank.account.domain.account.CsUserAccountDetail;
import com.itanbank.account.domain.account.CsWithdrawDetail;
import com.itanbank.account.domain.web.enums.SettleTypeEnum;
import com.itanbank.account.pay.enums.BusiTypeEnum;
import com.itanbank.account.pay.enums.ComAmtTypeEnum;
import com.itanbank.account.pay.enums.ExecuetResultCode;
import com.itanbank.account.pay.enums.TranStateEnum;
import com.itanbank.account.pay.model.query.DowmLoanFileRequest;
import com.itanbank.account.pay.model.query.DowmLoanFileResult;
import com.itanbank.account.pay.model.query.TransferRequest;
import com.itanbank.account.pay.model.query.TransferResult;
import com.itanbank.account.service.CsProjectDetailService;
import com.itanbank.account.service.CsProjectTransferDetailService;
import com.itanbank.account.service.CsRechargeDetailService;
import com.itanbank.account.service.CsUserAccountDetailService;
import com.itanbank.account.service.CsWithdrawDetailService;
import com.itanbank.account.service.ReconciliationService;

import net.sf.json.JSONObject;
/**
 * 对账管理控制类
 * @author pc
 *
 */
@Controller
public class ReconciliationController {
	
	@Autowired
	private ReconciliationService reconciliationService;
	
	@Autowired
	private CsProjectDetailService csProjectDetailService;
	
	@Autowired
	private CsRechargeDetailService csRechargeDetailService;
	
	@Autowired
	private CsWithdrawDetailService csWithdrawDetailService;
	
	@Autowired
	private CsProjectTransferDetailService csProjectTransferDetailService;
	
	@Autowired
	private CsUserAccountDetailService csUserAccountDetailService;
	
	@RequestMapping("/reconciliation/dowmLoanFileList.html")
    public String dowmLoanFileList(){
        return "/reconciliation/dowmLoanFileList";
    }
	
	@Value("${dowmloan.file.path}")
	private String downLoanPath;
	
	/**
	 * 下载文件
	 * @param request
	 * @param dowmLoanFileRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/reconciliation/dowmLoanFile.json")
    @ResponseBody
    public DowmLoanFileResult dowmLoanFile(HttpServletRequest request, DowmLoanFileRequest dowmLoanFileRequest) throws Exception{
	   String path = request.getServletContext().getRealPath(downLoanPath);
	   String date = dowmLoanFileRequest.getSettle_date_p2p();
	   StringBuffer sb =  new StringBuffer();
	   sb.append(path);
	   sb.append(File.separator);
	   sb.append(date);
	   StringBuffer fileName = new StringBuffer();
	   fileName.append(dowmLoanFileRequest.getSettle_type_p2p());
	   fileName.append(date);
	   fileName.append(".txt");
	   DowmLoanFileResult dowmLoanFileResult = reconciliationService.downLoanFile(dowmLoanFileRequest,sb.toString(),fileName.toString());
       return dowmLoanFileResult;
    }
	
	/**
	 * 导入文件
	 * @param request
	 * @param dowmLoanFileRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/reconciliation/upLoanFile.json")
    @ResponseBody
    public JSONObject upLoanFile(HttpServletRequest request, HttpServletResponse response, DowmLoanFileRequest dowmLoanFileRequest) throws Exception{
		JSONObject jSONObject = new JSONObject();
		String path = request.getServletContext().getRealPath(downLoanPath);
		String date = dowmLoanFileRequest.getSettle_date_p2p();
		StringBuffer sb =  new StringBuffer();
		sb.append(path);
		sb.append(File.separator);
		sb.append(date);
		StringBuffer fileName = new StringBuffer();
		fileName.append(dowmLoanFileRequest.getSettle_type_p2p());
		fileName.append(date);
		fileName.append(".txt");
		List<?> dlist = null;
		//查询批次是否导入
		if(SettleTypeEnum.ONE.getCode().equals(dowmLoanFileRequest.getSettle_type_p2p())){
			dlist = csRechargeDetailService.queryCsRechargeDetailByBatchNo(date);
		}else if(SettleTypeEnum.TWO.getCode().equals(dowmLoanFileRequest.getSettle_type_p2p())){
			dlist = csWithdrawDetailService.queryCsWithdrawDetailByBatchNo(date);
		}else if(SettleTypeEnum.THREE.getCode().equals(dowmLoanFileRequest.getSettle_type_p2p())){
			dlist = csProjectDetailService.queryCsProjectDetailByBatchNo(date);
		}else if(SettleTypeEnum.FOUR.getCode().equals(dowmLoanFileRequest.getSettle_type_p2p())){
			dlist = csProjectTransferDetailService.queryCsProjectTransferDetailByBatchNo(date);
		}else if(SettleTypeEnum.SIX.getCode().equals(dowmLoanFileRequest.getSettle_type_p2p())){
			dlist = csUserAccountDetailService.queryCsUserAccountDetailByBatchNo(date);
		}
		if(null!=dlist && dlist.size()>0){
			jSONObject.put("code", "9999");
			jSONObject.put("desc", "该对账日期文件已导入，请勿重复操作");
		}else{
			jSONObject = reconciliationService.upLoanFile(dowmLoanFileRequest,sb.toString(),fileName.toString());
		}
		JSONUtils.printObject(jSONObject,response);
		return null;
    }
	
	

}
