package com.itanbank.account.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.itanbank.account.domain.web.*;
import com.itanbank.account.domain.web.enums.BusinessTypeEnum;
import com.itanbank.account.domain.web.enums.OrderStatusEnum;
import com.itanbank.account.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.itanbank.account.domain.web.enums.ProjectStatusEnum;
import com.itanbank.account.domain.web.enums.RechargeStatusEnum;
import com.itanbank.account.repository.web.ItbRechargeInfoRepository;
import com.itanbank.account.service.back.ItbBackAmountService;

/**
 * 定时任务配置
 * 
 * CRON表达式    含义 
 * "0 0 12 * * ?"    每天中午十二点触发 
 * "0 15 10 ? * *"    每天早上10：15触发 
 * "0 15 10 * * ?"    每天早上10：15触发 
 * "0 15 10 * * ? *"    每天早上10：15触发 
 * "0 15 10 * * ? 2005"    2005年的每天早上10：15触发 
 * "0 * 14 * * ?"    每天从下午2点开始到2点59分每分钟一次触发 
 * "0 0/5 14 * * ?"    每天从下午2点开始到2：55分结束每5分钟一次触发 
 * "0 0/5 14,18 * * ?"    每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发 
 * "0 0-5 14 * * ?"    每天14:00至14:05每分钟一次触发 
 * "0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发 
 * "0 15 10 ? * MON-FRI"    每个周一、周二、周三、周四、周五的10：15触发
 */
@Component
public class ScheduledTasks {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItbRechargeInfoService itbRechargeInfoService;
    @Autowired
    private ItbRechargeInfoRepository itbRechargeInfoRepository;
    @Autowired
    private ItbProjectInfoService itbProjectInfoService;
    @Autowired
    private ItbProjectAccountService itbProjectAccountService;
    @Autowired @Qualifier("itbBackUserAmountServiceImpl")
    private ItbBackAmountService itbBackUserAmountServiceImpl;
    @Autowired @Qualifier("itbBackAdvanceAmountServiceImpl")
    private ItbBackAmountService itbBackAdvanceAmountServiceImpl;

    @Autowired
    private  ItbRepayInfoService  itbRepayInfoService;
    @Autowired
    private ItbAdvanceService itbAdvanceService;
    @Autowired
    private ItbOrderInfoService itbOrderInfoService;
    @Autowired
    private ItbInvestService itbInvestService;
    @Autowired
    private VItbProjectandbackAdvanceService  vItbProjectandbackAdvanceService;
    @Autowired
    private BackupService backupService;

    @Value("${order.nopay.timerout}")
    private long timeout = 600;

    /*@Scheduled(fixedRate = 30000)
    public void reportCurrentTime() {
        logger.info("申请件分配定时任务");
    }*/

    @Scheduled(fixedRate = 30000)
    public void reportCurrentTime()throws Exception {
        logger.info("查询企业充值中间状态充值记录开始...");
        List<ItbRechargeInfo> unKonwnRechargeList = itbRechargeInfoRepository.findByStatus(RechargeStatusEnum.MIDDLE_STATE.getCode());
        if(unKonwnRechargeList.size()>0){
            //说明当前数据库存在中间状态的充值订单,查询订单时间
            for(ItbRechargeInfo rechargeInfo:unKonwnRechargeList){
                if(rechargeInfo.getCompanyId() != null) {
                    itbRechargeInfoService.handleUnkonwnRechargeInfoCompany(rechargeInfo);
                } else {
                    itbRechargeInfoService.handleUnkonwnRechargeInfoPerson(rechargeInfo);
                }
            }
        }
    }


    @Scheduled(cron="0 0 1 * * ?")
    public  void  addPenInterestDay()throws Exception{
    	logger.info("查询逾期件开始,新增逾期表数据..");
    	List<ItbRepayInfo>  repayList = itbRepayInfoService.queryOverdueInfo();
    	if(repayList.size() > 0){
    		itbRepayInfoService.addPenInterestDay(repayList);
    	}
    }

    @Scheduled(cron="0 0 2 * * ?")
    public void calculateAdvanceAmount() {
        logger.info("计算代偿金额定时任务开始...");
        try {
            itbAdvanceService.calculateAdvanceAmount();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("计算代偿金额定时任务异常", e);
        }
        logger.info("计算代偿金额定时任务结束...");
    }

    @Scheduled(fixedRate = 30000)
    public void checkIsBackComplete() throws Exception {
        logger.info("检查是否返款完成...");
        // 查询返款中的标的
        List<ItbProjectInfo> list = itbProjectInfoService.findByStatus(ProjectStatusEnum.BACK.getCode());
        for(ItbProjectInfo projectInfo : list){
            ItbProjectAccount projectAccount = itbProjectAccountService.findById(projectInfo.getId());
            // 检查是否返款完成
            itbBackUserAmountServiceImpl.updateProjectStatus(projectInfo, projectAccount);
        }

        // 查询返款到垫付方的标
        List<ItbProjectInfo> list2 = itbProjectInfoService.findForAdvance();
        for(ItbProjectInfo projectInfo: list2){
            ItbProjectAccount projectAccount = itbProjectAccountService.findById(projectInfo.getId());
            itbBackAdvanceAmountServiceImpl.updateProjectStatus(projectInfo, projectAccount);
        }
    }


    @Scheduled(fixedRate = 60000)
    public void checkInverstOrder() throws Exception {
        logger.info("检查超过10分钟的投资订单，使订单失败开始...");
        List<ItbOrderInfo> orderInfos = itbOrderInfoService.findTimeroutOrderByBusinessTypeAndOrderStatus(BusinessTypeEnum.INVEST.getCode(), OrderStatusEnum.CREATE.getCode(), timeout);
        for(ItbOrderInfo orderInfo : orderInfos){
            itbInvestService.cancelTimeoutOrder(orderInfo);
        }
        logger.info("检查超过10分钟的投资订单，使订单失败结束...");
    }
    
//    /**
//     * 备份资金流水
//     * @throws Exception
//     */
//    @Scheduled(cron="10 0 0 * * ?")
//    public void backupCapitalInfoTime() throws Exception {
//    	try{
//    		logger.info("备份资金流水信息开始...");
//        	String batchNo = getBatchNo("yyyyMMdd");
//        	StringBuffer date = new StringBuffer();
//        	date.append("%");
//        	date.append(getBatchNo("yyyy-MM-dd"));
//        	date.append("%");
//        	backupService.backUpCapitalInfo(batchNo,date.toString());
//        	logger.info("备份资金流水信息结束...");
//    	}catch(Exception e){
//    		e.printStackTrace();
//    		logger.info("备份资金流水信息异常..."+e.getMessage());
//    	}
//    }
    
    
    /**
     * 备份标的
     * @throws Exception
     */
    @Scheduled(cron="30 0 0 * * ?")
    public void backupProjectInfoTime() throws Exception {
    	try{
    		logger.info("备份标的信息开始...");
        	String batchNo = getBatchNo("yyyyMMdd");
        	backupService.backUpProjectInfo(batchNo);
        	logger.info("备份标的信息结束...");
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.info("备份标的信息异常..."+e.getMessage());
    	}
    	
    }
    
    /**
     * 备份公司账户
     * @throws Exception
     */
    @Scheduled(cron="0 1 0 * * ?")
    public void backupCompanyInfoTime() throws Exception {
    	try{
    		logger.info("备份公司账户信息开始...");
        	String batchNo = getBatchNo("yyyyMMdd");
        	backupService.backUpCompanyInfo(batchNo);
        	logger.info("备份公司账户信息结束...");
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.info("备份公司账户信息异常..."+e.getMessage());
    	}
    }
    
    /**
     * 备份用户信息
     * @throws Exception
     */
    @Scheduled(cron="30 1 0 * * ?")
    public void backupUserInfoTime() throws Exception {
    	try{
    		logger.info("备份用户信息开始...");
        	String batchNo = getBatchNo("yyyyMMdd");
        	backupService.backUpUserInfo(batchNo);
        	logger.info("备份用户信息结束...");
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.info("备份用户信息异常..."+e.getMessage());
    	}
    }
    
    
    
    private String getBatchNo(String format){
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DATE, -1); //得到前一天
    	Date date = calendar.getTime();
    	DateFormat df = new SimpleDateFormat(format);
    	return df.format(date);
    	
    }
}
