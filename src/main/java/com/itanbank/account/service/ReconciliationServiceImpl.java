package com.itanbank.account.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.epbcommons.httpclient.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itanbank.account.common.HttpDownloadUtil;
import com.itanbank.account.domain.account.CsProjectDetail;
import com.itanbank.account.domain.account.CsProjectDetailInvest;
import com.itanbank.account.domain.account.CsProjectTransferDetail;
import com.itanbank.account.domain.account.CsRechargeDetail;
import com.itanbank.account.domain.account.CsUserAccountDetail;
import com.itanbank.account.domain.account.CsWithdrawDetail;
import com.itanbank.account.domain.web.enums.SettleTypeEnum;
import com.itanbank.account.pay.common.JsoupUtil;
import com.itanbank.account.pay.enums.ExecuetResultCode;
import com.itanbank.account.pay.model.query.DowmLoanFileRequest;
import com.itanbank.account.pay.model.query.DowmLoanFileResult;
import com.umpay.api.common.ReqData;
import com.umpay.api.paygate.v40.Mer2Plat_v40;

import net.sf.json.JSONObject;

@Service
public class ReconciliationServiceImpl implements ReconciliationService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CsProjectDetailService csProjectDetailService;
	
	@Autowired
	private CsProjectDetailInvestService csProjectDetailInvestService;
	
	@Autowired
	private CsRechargeDetailService csRechargeDetailService;
	
	@Autowired
	private CsWithdrawDetailService csWithdrawDetailService;
	
	@Autowired
	private CsProjectTransferDetailService csProjectTransferDetailService;
	
	@Autowired
	private CsUserAccountDetailService csUserAccountDetailService;
	
	@Override
	public DowmLoanFileResult downLoanFile(DowmLoanFileRequest dowmLoanFileRequest,String path,String fileName) throws Exception{
		logger.info("对账文件下载请求参数："+dowmLoanFileRequest.toString());
        Map<String, String> paramMap = BeanUtils.describe(dowmLoanFileRequest);
        ReqData reqData = Mer2Plat_v40.makeReqDataByGet(paramMap);
        String url = reqData.getUrl();
        logger.info("对账文件下载请求url："+url);
        DowmLoanFileResult dowmLoanFileResult = new DowmLoanFileResult();
        if(HttpDownloadUtil.download(url, path,fileName)){
        	dowmLoanFileResult.setRet_code(ExecuetResultCode.SCUESS.getCode());
        }else{
        	dowmLoanFileResult.setRet_code(ExecuetResultCode.E9999.getCode());
        }
        return dowmLoanFileResult;
		
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
	@Override
	public JSONObject upLoanFile(DowmLoanFileRequest dowmLoanFileRequest, String path, String fileName)
			throws Exception {
		JSONObject jsonObject = new JSONObject();
		File file = new File(path+File.separator+fileName);
		if(!file.exists()){
			jsonObject.put("code", "9999");
			jsonObject.put("desc", "文件不存在，请先下载文件");
		}else{
			InputStreamReader readFile = new InputStreamReader(new FileInputStream(file),"UTF-8");//考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(readFile);
			String lineTxt = null;
			Date time = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			int record = 0;
            while((lineTxt = bufferedReader.readLine()) != null){
            	if(!(lineTxt.indexOf("TRADEDETAIL-START")>=0 || lineTxt.indexOf("TRADEDETAIL-END")>=0)){
            		String[] dataArr =null;
            		if(SettleTypeEnum.SIX.getCode().equals(dowmLoanFileRequest.getSettle_type_p2p())){
            			dataArr = lineTxt.split("\\|");
            		}else{
            			dataArr = lineTxt.split(",");
            		}
            		if(SettleTypeEnum.ONE.getCode().equals(dowmLoanFileRequest.getSettle_type_p2p())){//充值交易明细文件
            			saveCsRechargeDetail(dataArr,dowmLoanFileRequest.getSettle_date_p2p(),time,sf);
            		}else if(SettleTypeEnum.TWO.getCode().equals(dowmLoanFileRequest.getSettle_type_p2p())){//提现对账文件
            			saveCsWithdrawDetail(dataArr,dowmLoanFileRequest.getSettle_date_p2p(),time,sf);
            		}else if(SettleTypeEnum.THREE.getCode().equals(dowmLoanFileRequest.getSettle_type_p2p())){//标的对账文件
            			saveCsProjectDetail(dataArr,dowmLoanFileRequest.getSettle_date_p2p(),time,sf);
            		}else if(SettleTypeEnum.FOUR.getCode().equals(dowmLoanFileRequest.getSettle_type_p2p())){//标的转账
            			saveCsProjectTransferDetail(dataArr,dowmLoanFileRequest.getSettle_date_p2p(),time,sf);
            		}else if(SettleTypeEnum.SIX.getCode().equals(dowmLoanFileRequest.getSettle_type_p2p())){//托管用户开户对账文件
            			saveCsUserAccountDetail(dataArr,dowmLoanFileRequest.getSettle_date_p2p(),time,sf);
            		}
            		record++;
            	}
            }
            readFile.close();
            jsonObject.put("code", "0000");
            jsonObject.put("total", record);
			jsonObject.put("desc", "文件导入完成");
		}
		return jsonObject;
	}
	
	/**
	 * 保存充值交易明细文件
	 * @param dataArr
	 * @param batchNo
	 * @param time
	 * @param sf
	 */
	private void saveCsRechargeDetail(String[] dataArr,String batchNo, Date time,SimpleDateFormat sf) throws Exception{
		CsRechargeDetail csRechargeDetail = new CsRechargeDetail();
		csRechargeDetail.setOrderNo(dataArr[0]);//平台请求流水号
		csRechargeDetail.setTradeDate(sf.parse(dataArr[1]));//平台交易日期
		csRechargeDetail.setPayAccountNo(dataArr[2]);//账户号
		csRechargeDetail.setMerNo(dataArr[3]);//金账户托管平台用户号/商户号
		csRechargeDetail.setAmount(new Double(dataArr[4]));//金额
		csRechargeDetail.setMerDate(dataArr[5]);//资金账户托管平台日期
		csRechargeDetail.setMerTime(dataArr[6]);//资金账户托管平台时间
		csRechargeDetail.setTradeNo(dataArr[7]);//交易平台流水号
		csRechargeDetail.setComAmt(new Double(dataArr[8]));//手续费金额
		csRechargeDetail.setComAmtType(dataArr[9]);//手续费承担方类型
		csRechargeDetail.setProductNo(dataArr[10]);//充值产品号
		csRechargeDetail.setCreateTime(time);//日期
		csRechargeDetail.setBatchNo(batchNo);//批次
		csRechargeDetailService.saveCsRechargeDetail(csRechargeDetail);
	}
	
	/**
	 * 保存提现对账文件
	 * @param dataArr
	 * @param batchNo
	 * @param time
	 * @param sf
	 */
	private void saveCsWithdrawDetail(String[] dataArr,String batchNo, Date time,SimpleDateFormat sf) throws Exception{
		CsWithdrawDetail csWithdrawDetail = new CsWithdrawDetail();
		csWithdrawDetail.setMerNo(dataArr[0]);//商户号
		csWithdrawDetail.setTradeType(dataArr[1]);//交易类型
		csWithdrawDetail.setOrderNo(dataArr[2]);//商户订单号
		csWithdrawDetail.setOrderDate(sf.parse(dataArr[3]));//订单日期
		csWithdrawDetail.setAmount(new Double(dataArr[4]));//交易金额
		csWithdrawDetail.setComAmt(new Double(dataArr[5]));//手续费
		csWithdrawDetail.setCsDate(sf.parse(dataArr[6]));//对账日期
		csWithdrawDetail.setWriteDate(sf.parse(dataArr[7]));//记账日期
		csWithdrawDetail.setTradeStatusCn(dataArr[8]);//交易状态（中文描述）
		csWithdrawDetail.setTradeNo(dataArr[9]);//交易平台流水号
		csWithdrawDetail.setComAmtType(dataArr[10]);//手续费承担方类型
		csWithdrawDetail.setProductNo(dataArr[11]);//提现产品号
		csWithdrawDetail.setCreateTime(time);//日期
		csWithdrawDetail.setBatchNo(batchNo);//批次
		csWithdrawDetailService.saveCsWithdrawDetail(csWithdrawDetail);
	}
	/**
	 * 保存标的对账文件
	 * @param dataArr
	 * @param batchNo
	 * @param time
	 * @param sf
	 * @throws Exception
	 */
	private void saveCsProjectDetail(String[] dataArr,String batchNo, Date time,SimpleDateFormat sf) throws Exception{
		CsProjectDetail csProjectDetail = new CsProjectDetail();
		csProjectDetail.setProjectId(Long.parseLong(dataArr[0]));//标的号
		csProjectDetail.setProjectAccountNo(dataArr[1]);//标的账户号
		csProjectDetail.setStatus(dataArr[2]);//状态
		csProjectDetail.setBalance(new Double(dataArr[3]));//余额
		csProjectDetail.setCreateTime(sf.parse(dataArr[4]));//创建日期
		csProjectDetail.setLoanPerson(dataArr[6]);//借款人列表
		csProjectDetail.setUsePerson(dataArr[7]);//资金使用人列表
		csProjectDetail.setAdvancePerson(dataArr[8]);//担保人列表
		csProjectDetail.setPaymentPersion(dataArr[9]);//缴费方列表
		csProjectDetail.setCreateDate(time);//日期
		csProjectDetail.setBatchNo(batchNo);//批次
		csProjectDetail = csProjectDetailService.saveCsProjectDetail(csProjectDetail);
		String loanPersonStr = dataArr[5];//投资人列表
		if(null!=loanPersonStr && !"".equals(loanPersonStr)){
			String[] loanPersonArr = loanPersonStr.split("\\|");
			for(int i=0;i<loanPersonArr.length;i++){
				CsProjectDetailInvest csProjectDetailInvest = new CsProjectDetailInvest();
				csProjectDetailInvest.setInvestPerson(loanPersonArr[i]);
				csProjectDetailInvest.setProjectDetailId(csProjectDetail.getId());
				csProjectDetailInvestService.saveCsProjectDetailInvest(csProjectDetailInvest);
			}
		}
	}
	
	/**
	 * 保存标的转账文件
	 * @param dataArr
	 * @param batchNo
	 * @param time
	 * @param sf
	 * @throws Exception
	 */
	private void saveCsProjectTransferDetail(String[] dataArr,String batchNo, Date time,SimpleDateFormat sf) throws Exception{
		CsProjectTransferDetail csProjectTransferDetail = new CsProjectTransferDetail();
		csProjectTransferDetail.setOrderNo(dataArr[0]);//平台请求流水号
		csProjectTransferDetail.setTradeDate(sf.parse(dataArr[1]));//平台交易日期
		csProjectTransferDetail.setProjectId(Long.parseLong(dataArr[2]));//标的号
		csProjectTransferDetail.setOutAccountNo(dataArr[3]);//付款方账户号
		csProjectTransferDetail.setInAccountNo(dataArr[4]);//收款方账户号
		csProjectTransferDetail.setProjectAccountNo(dataArr[5]);//标的账户号
		csProjectTransferDetail.setAmount(new Double(dataArr[6]));//金额
		csProjectTransferDetail.setTransAction(dataArr[7]);//转账方向
		csProjectTransferDetail.setServType(dataArr[8]);//业务类型
		csProjectTransferDetail.setMerDate(dataArr[9]);//支付平台日期
		csProjectTransferDetail.setMerTime(dataArr[10]);//支付平台时间
		csProjectTransferDetail.setTradeNo(dataArr[11]);//交易平台流水号
		csProjectTransferDetail.setAccountDate(sf.parse(dataArr[12]));//账户日期
		csProjectTransferDetail.setCreateTime(time);//日期
		csProjectTransferDetail.setBatchNo(batchNo);//批次
		csProjectTransferDetailService.saveCsProjectTransferDetail(csProjectTransferDetail);
	}
	
	/**
	 * 保存托管用户开户对账文件
	 * @param dataArr
	 * @param batchNo
	 * @param time
	 * @param sf
	 * @throws Exception
	 */
	private void saveCsUserAccountDetail(String[] dataArr,String batchNo, Date time,SimpleDateFormat sf) throws Exception{
		CsUserAccountDetail csUserAccountDetail = new CsUserAccountDetail();
		csUserAccountDetail.setUserId(Long.parseLong(dataArr[0]));//商户用户标识
		csUserAccountDetail.setPayAccountId(Long.parseLong(dataArr[1]));//资金账户托管平台用户号
		csUserAccountDetail.setPayAccountNo(dataArr[2]);//资金账户托管平台账户号
		csUserAccountDetail.setName(dataArr[3]);//姓名
		csUserAccountDetail.setIdCard(dataArr[4]);//身份证
		csUserAccountDetail.setMobile(dataArr[5]);//手机
		csUserAccountDetail.setMail(dataArr[6]);//邮箱
		csUserAccountDetail.setOpenDate(sf.parse(dataArr[7]));//开户日期
		csUserAccountDetail.setCreateTime(time);//日期
		csUserAccountDetail.setBatchNo(batchNo);//批次
		csUserAccountDetailService.saveCsUserAccountDetail(csUserAccountDetail);
	}
	
}
