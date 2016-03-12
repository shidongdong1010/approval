package com.itanbank.account.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.itanbank.account.domain.web.enums.BusinessTypeEnum;
import org.epbcommons.transformation.math.MathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itanbank.account.common.IpHelper;
import com.itanbank.account.common.JsonResult;
import com.itanbank.account.common.JsonResultHelper;
import com.itanbank.account.common.page.JqGridPage;
import com.itanbank.account.common.page.PageUtils;
import com.itanbank.account.domain.app.enums.YesOrNoEnum;
import com.itanbank.account.domain.web.ItbBackAdvanceInfo;
import com.itanbank.account.domain.web.ItbBackPlatformInfo;
import com.itanbank.account.domain.web.ItbBackUserInfo;
import com.itanbank.account.domain.web.ItbInvestInfo;
import com.itanbank.account.domain.web.ItbProjectAccount;
import com.itanbank.account.domain.web.ItbProjectInfo;
import com.itanbank.account.domain.web.ItbRepayInfo;
import com.itanbank.account.domain.web.VItbProjectandbackAdvance;
import com.itanbank.account.domain.web.VItbProjectandbackPlatform;
import com.itanbank.account.domain.web.bo.InvestBackUserBo;
import com.itanbank.account.domain.web.bo.RepayBackAdvanceBo;
import com.itanbank.account.domain.web.bo.RepayBackPlatformBo;
import com.itanbank.account.domain.web.enums.ItbInvestStatusEnum;
import com.itanbank.account.domain.web.enums.ProjectStatusEnum;
import com.itanbank.account.domain.web.enums.UserTypeEnum;
import com.itanbank.account.domain.web.resultcode.BackResultCode;
import com.itanbank.account.pay.enums.ExecuetResultCode;
import com.itanbank.account.pay.enums.ServTypeEnum;
import com.itanbank.account.pay.model.project.ProjectTransferResult;
import com.itanbank.account.service.ItbBackAdvanceInfoService;
import com.itanbank.account.service.ItbBackPlatformInfoService;
import com.itanbank.account.service.ItbBackUserInfoService;
import com.itanbank.account.service.ItbInvestService;
import com.itanbank.account.service.ItbProjectAccountService;
import com.itanbank.account.service.ItbProjectInfoService;
import com.itanbank.account.service.ItbRepayInfoService;
import com.itanbank.account.service.VItbProjectandbackAdvanceService;
import com.itanbank.account.service.VItbProjectandbackPlatformService;
import com.itanbank.account.service.back.ItbBackAmountFactory;
import com.itanbank.account.service.back.ItbBackAmountService;

/**
 * 返款管理
 * Created by SDD on 2016/2/26.
 */
@Controller
public class ItbBackInfoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItbProjectInfoService itbProjectInfoService;
    @Autowired
    private ItbProjectAccountService itbProjectAccountService;
    @Autowired
    private ItbInvestService itbInvestService;
    @Autowired
    private ItbRepayInfoService repayInfoService;
    @Autowired
    private ItbBackUserInfoService itbBackUserInfoService;
    @Autowired
    private ItbBackAdvanceInfoService itbBackAdvanceInfoService;
    @Autowired
    private JsonResultHelper jsonResultHelper;
    @Autowired
    private VItbProjectandbackAdvanceService  vitbProjectandbackAdvanceService;
    @Autowired
    private VItbProjectandbackPlatformService  vitbProjectandbackPlatformService;
    @Autowired
    private ItbBackPlatformInfoService itbBackPlatformInfoService;

    @RequestMapping("/backAmountUserList.html")
    public  String  backUserList(){
        return "/loanManageMent/backAmountUserList";
    }

    /**
     * 返款信息列表查询
     *
     * @return
     */
    @RequestMapping("/backAmountUserList.json")
    @ResponseBody
    public JqGridPage backAmountUserList(ItbProjectInfo itbProjectInfo, HttpServletRequest request) {
        logger.info("查询返款的标的的信息...");
        itbProjectInfo.setStatus(ProjectStatusEnum.BACK.getCode());
        Pageable pageable = PageUtils.getPageable(request);
        Page<ItbProjectInfo> pages = itbProjectInfoService.findPage(itbProjectInfo, pageable);
        return PageUtils.toJqGridPage(pages);
    }

    /**
     * 返款到投资人列表查询
     */
    @RequestMapping("/backInsertList.json")
    @ResponseBody
    public JqGridPage backInsertList(Long projectId){
        logger.info("查询返款的到投资人列表信息...");
        List<ItbInvestInfo> itbInvestInfos = itbInvestService.findByProjectIdAndStatus(projectId, ItbInvestStatusEnum.FINISH.getCode());
        List<ItbBackUserInfo> itbBackUserInfos = itbBackUserInfoService.findByProjectId(projectId);
        List<InvestBackUserBo> investBackUserBos = new ArrayList<InvestBackUserBo>();

        double investAmount = 0d;  // 还款金额
        double returnAmount = 0d;  // 收益金额
        double backCapital = 0d;   // 返款本金
        double backReturnAmount = 0d; // 返款金额

        for(ItbInvestInfo investInfo : itbInvestInfos){
            InvestBackUserBo investBackUserBo = new InvestBackUserBo();
            investBackUserBo.setInvestInfo(investInfo);

            investAmount = MathUtil.add(investAmount, investInfo.getAmount());
            returnAmount = MathUtil.add(returnAmount, investInfo.getReturnAmount());
            for(ItbBackUserInfo backUserInfo : itbBackUserInfos){
                if(investInfo.getId().equals(backUserInfo.getInvestId())){
                    backCapital = MathUtil.add(backCapital, backUserInfo.getRepayCapital());
                    backReturnAmount = MathUtil.add(backReturnAmount, backUserInfo.getReturnAmount());
                    investBackUserBo.setBackUserInfo(backUserInfo);
                    break;
                }
            }
            investBackUserBos.add(investBackUserBo);
        }

        Map<String, Object> userdata = new HashMap<String, Object>();
        userdata.put("investInfo.userName", "汇总:");
        userdata.put("investInfo.amount", investAmount);
        userdata.put("investInfo.returnAmount", returnAmount);
        userdata.put("backUserInfo.repayCapital", backCapital);
        userdata.put("backUserInfo.returnAmount", backReturnAmount);

        JqGridPage jqGridPage = new JqGridPage();
        // 注意spring data pagess是第一页是从0开始的，所以此处+1了
        jqGridPage.setPage(1);
        jqGridPage.setTotal(1);
        jqGridPage.setRecords(investBackUserBos.size());
        jqGridPage.setRows(investBackUserBos);
        jqGridPage.setUserdata(userdata);
        return jqGridPage;
    }

    /**
     * 返款到投资用户
     * @param projectId
     * @param request
     * @return
     */
    @RequestMapping("/backAmountUser.json")
    @ResponseBody
    public JsonResult backAmountUser(Long projectId, HttpServletRequest request) throws Exception {
        logger.info("返款到投资用户...");
        ItbProjectInfo projectInfo = itbProjectInfoService.findById(projectId);
        ItbProjectAccount projectAccount = itbProjectAccountService.findById(projectId);
        List<ItbInvestInfo> investInfoList = itbInvestService.findByProjectId(projectId);
        // 用户IP地址
        String ip = IpHelper.getIpAddress(request);

        for(ItbInvestInfo investInfo : investInfoList) {
            logger.info("------------------------------投资人{}, 返款申请", investInfo);

            // 得到业务处理类
            ItbBackAmountService backService = ItbBackAmountFactory.getBackService(ServTypeEnum.REPAY_BACK.getCode(), investInfo.getInvestUserType());

            ProjectTransferResult projectTransferResult = backService.addOrderInfo(projectInfo, projectAccount, investInfo, ip);
            if(projectTransferResult == null){
                logger.info("投资人{}, 返款申请已经处理，本次无需重复操作", investInfo);
                continue;
            }

            Thread.sleep(1000);   // 暂停1秒种处理下一个投资人

            ExecuetResultCode resultCode = ExecuetResultCode.getByCode(projectTransferResult.getRet_code());
            if(!ExecuetResultCode.SCUESS.equals(resultCode)){
                logger.info("返款申请失败{}", resultCode);
                return jsonResultHelper.buildFailJsonResult(BackResultCode.SYSTEM_ERROR.getStatusCode(), projectTransferResult.getRet_code(), projectTransferResult.getRet_msg());
            }
        }
        return jsonResultHelper.buildSuccessJsonResult(null);
    }


    @RequestMapping("/backAmountAdvanceList.html")
    public  String  backAmountAdvanceList(){
        return "/loanManageMent/backAmountAdvanceList";
    }

    /**
     * 返款到垫付公司查询
     *
     * @return
     */
    @RequestMapping("/backAmountAdvanceList.json")
    @ResponseBody
    public JqGridPage backAmountAdvanceList(VItbProjectandbackAdvance itbProjectandbackAdvance, HttpServletRequest request) {
        logger.info("查询返款的标的的信息...");
        Pageable pageable = PageUtils.getPageable(request);
        Page<VItbProjectandbackAdvance> pages = vitbProjectandbackAdvanceService.findPage(itbProjectandbackAdvance, pageable);
        return PageUtils.toJqGridPage(pages);
    }

    /**
     * 返款到垫付公司-垫付记录
     */
    @RequestMapping("/backAdvance.json")
    @ResponseBody
    public List<RepayBackAdvanceBo> backAdvance(Long projectId){
        logger.info("查询返款的到投资人列表信息...");
        ItbRepayInfo repayInfo = repayInfoService.findByProjectId(projectId);
        ItbProjectAccount projectAccount = itbProjectAccountService.findById(projectId);
        ItbBackAdvanceInfo backAdvanceInfo = itbBackAdvanceInfoService.findByProjectIdAndCompanyId(projectId, projectAccount.getAdvanceCompanyId());

        List<RepayBackAdvanceBo> list = new ArrayList<RepayBackAdvanceBo>();
        RepayBackAdvanceBo repayBackAdvanceBo = new RepayBackAdvanceBo();
        repayBackAdvanceBo.setBackAdvanceInfo(backAdvanceInfo);
        repayBackAdvanceBo.setRepayInfo(repayInfo);
        list.add(repayBackAdvanceBo);
        return list;
    }

    /**
     * 返款到垫付公司
     * @param projectId
     * @param request
     * @return
     */
    @RequestMapping("/backAmountAdvance.json")
    @ResponseBody
    public JsonResult backAmountAdvance(Long projectId, HttpServletRequest request) throws Exception {
        logger.info("返款到垫付公司...");
        ItbProjectInfo projectInfo = itbProjectInfoService.findById(projectId);
        ItbProjectAccount projectAccount = itbProjectAccountService.findById(projectId);
        // 用户IP地址
        String ip = IpHelper.getIpAddress(request);
        // 得到业务处理类
        ItbBackAmountService backService = ItbBackAmountFactory.getBackService(ServTypeEnum.ADVANCE_BACK.getCode(), UserTypeEnum.ENTERPRISE.getCode());

        if((YesOrNoEnum.Y.getCode()+"").equals(projectAccount.getIsAdvance())){ // 是否垫付
            logger.info("垫付公司{}, 返款申请", projectAccount.getAdvanceCompanyId());

            ProjectTransferResult projectTransferResult = backService.addOrderInfo(projectInfo, projectAccount, null, ip);

            if(projectTransferResult == null){
                logger.info("标的账户{}, 返款申请已经处理，本次无需重复操作", projectAccount);
                return jsonResultHelper.buildFailJsonResult(BackResultCode.BACK_REPEAT_ACTION);
            }

            ExecuetResultCode resultCode = ExecuetResultCode.getByCode(projectTransferResult.getRet_code());
            if (!ExecuetResultCode.SCUESS.equals(resultCode)) {
                logger.info("返款申请失败{}", resultCode);
                return jsonResultHelper.buildFailJsonResult(BackResultCode.SYSTEM_ERROR.getStatusCode(), projectTransferResult.getRet_code(), projectTransferResult.getRet_msg());
            }
        }
        return jsonResultHelper.buildSuccessJsonResult(null);
    }

    
    /**
     * 初始化返款到垫付平台列表初始化
     * @return
     */
    @RequestMapping("/backAmountAdvancePlatformList.html")
    public  String  backAmountAdvancePlatformList(){
        return "/loanManageMent/backAmountPlatformList";
    }

    /**
     * 返款到垫付平台数据
     *
     * @return
     */
    @RequestMapping("/backAmountAdvancePlatformList.json")
    @ResponseBody
    public JqGridPage backAmountAdvancePlatformList(VItbProjectandbackPlatform itbProjectandbackPlatform, HttpServletRequest request) {
        logger.info("查询返款到平台的标的的信息...");
        Pageable pageable = PageUtils.getPageable(request);
        Page<VItbProjectandbackPlatform> pages = vitbProjectandbackPlatformService.findPage(itbProjectandbackPlatform, pageable);
        return PageUtils.toJqGridPage(pages);
    }

    /**
     * 返款到垫付公司-垫付记录
     */
    @RequestMapping("/backAdvancePlatform.json")
    @ResponseBody
    public List<RepayBackPlatformBo> backAdvancePlatform(Long projectId){
        logger.info("查询返款到平台的到投资人列表信息...");
        ItbRepayInfo repayInfo = repayInfoService.findByProjectId(projectId);
        ItbProjectAccount projectAccount = itbProjectAccountService.findById(projectId);
        ItbBackPlatformInfo backPlatformInfo = itbBackPlatformInfoService.findByProjectIdAndCompanyId(projectId, projectAccount.getAdvanceCompanyId());
        List<RepayBackPlatformBo> list = new ArrayList<RepayBackPlatformBo>();
        RepayBackPlatformBo repayBackPlatformBo = new RepayBackPlatformBo();
        repayBackPlatformBo.setBackPlatformInfo(backPlatformInfo);
        repayBackPlatformBo.setRepayInfo(repayInfo);
        list.add(repayBackPlatformBo);
        return list;
    }

    /**
     * 返款到平台
     * @param projectId
     * @param request
     * @return
     */
    @RequestMapping("/backAmountPlatform.json")
    @ResponseBody
    public JsonResult backAmountPlatform(Long projectId, HttpServletRequest request) throws Exception {
        logger.info("返款到平台...");
        ItbProjectInfo projectInfo = itbProjectInfoService.findById(projectId);
        ItbProjectAccount projectAccount = itbProjectAccountService.findById(projectId);
        // 用户IP地址
        String ip = IpHelper.getIpAddress(request);
        // 得到业务处理类
        ItbBackAmountService backService = ItbBackAmountFactory.getBackService(BusinessTypeEnum.LATE_REPAY_SERVER_AMOUNT.getCode(), UserTypeEnum.ENTERPRISE.getCode());

        if((YesOrNoEnum.Y.getCode()+"").equals(projectAccount.getIsAdvance())){ // 是否垫付
            logger.info("返款到平台{}, 返款申请", projectAccount.getAdvanceCompanyId());

            ProjectTransferResult projectTransferResult = backService.addOrderInfo(projectInfo, projectAccount, null, ip);

            if(projectTransferResult == null){
                logger.info("标的账户{}, 返款申请已经处理，本次无需重复操作", projectAccount);
                return jsonResultHelper.buildFailJsonResult(BackResultCode.BACK_REPEAT_ACTION);
            }

            ExecuetResultCode resultCode = ExecuetResultCode.getByCode(projectTransferResult.getRet_code());
            if (!ExecuetResultCode.SCUESS.equals(resultCode)) {
                logger.info("返款申请失败{}", resultCode);
                return jsonResultHelper.buildFailJsonResult(BackResultCode.SYSTEM_ERROR.getStatusCode(), projectTransferResult.getRet_code(), projectTransferResult.getRet_msg());
            }
        }
        return jsonResultHelper.buildSuccessJsonResult(null);
    }
}
