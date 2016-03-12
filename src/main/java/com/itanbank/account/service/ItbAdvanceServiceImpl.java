package com.itanbank.account.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.epbcommons.transformation.math.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itanbank.account.domain.web.ItbAdvanceInfo;
import com.itanbank.account.domain.web.ItbAdvanceRecordInfo;
import com.itanbank.account.domain.web.ItbBackAdvanceInfo;
import com.itanbank.account.domain.web.ItbCompanyInfo;
import com.itanbank.account.domain.web.ItbRepayInfo;
import com.itanbank.account.domain.web.enums.AdvanceRecordStatusEnum;
import com.itanbank.account.domain.web.enums.BusinessTypeEnum;
import com.itanbank.account.domain.web.enums.CompanyStatusEnum;
import com.itanbank.account.domain.web.enums.CompanyTypeEnum;
import com.itanbank.account.domain.web.enums.RepayStatusEnum;
import com.itanbank.account.repository.web.ItbAdvanceRepository;

/**
 * 偿付
 * @author wn
 *
 */
@Service
public class ItbAdvanceServiceImpl implements ItbAdvanceService{

	@Autowired
	private ItbAdvanceRepository itbAdvanceRepository;
	@Autowired
	private ItbRepayInfoService itbRepayInfoService;
	@Autowired
	private ItbCompanyInfoService itbCompanyInfoService;
	@Autowired
	private ItbAdvanceRecordService advanceRecordService;
	@Autowired
	private ItbCapitalFlowService itbCapitalFlowService;
	@Value("${advanceEndtime}")
	private String endtime;
	
	
	/**
	 * 保存对象
	 * @param advanceInfo
	 * @return
	 */
	@Override
	public ItbAdvanceInfo save(ItbAdvanceInfo advanceInfo){
		return itbAdvanceRepository.save(advanceInfo);
	}
	/**
	 * 根据垫付时间查询垫付信息
	 * @param advanceTime
	 * @return
	 */
	@Override
	public List<ItbAdvanceInfo> findByAdvanceTime(String advanceTime){
		return itbAdvanceRepository.findByAdvanceTime(advanceTime);
	}
	@Override
	public void delete(ItbAdvanceInfo entity){
		itbAdvanceRepository.delete(entity);
	}
	/**
	 * 计算代偿金额
	 * 代偿条件：当日到期且未还清的还款信息；
	 * 代偿金额：当期应还金额减指定时间前已还金额
	 * @throws Exception
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
	public void calculateAdvanceAmount() throws Exception{
		
		//删除当天代偿数据
		String advanceTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<ItbAdvanceInfo> advanceInfos = findByAdvanceTime(advanceTime);
		if(CollectionUtils.isNotEmpty(advanceInfos)){
			ItbAdvanceInfo itbAdvanceInfo = advanceInfos.get(0);
			List<ItbAdvanceRecordInfo> itbAdvanceRecordInfos = advanceRecordService.findByAdvanceId(itbAdvanceInfo.getId());
			advanceRecordService.deleteInBatch(itbAdvanceRecordInfos);
			delete(itbAdvanceInfo);
		}
		//代偿方企业信息
		ItbCompanyInfo itbCompanyInfo = null;
		List<ItbCompanyInfo> itbCompanyInfos = itbCompanyInfoService.findByTypeAndStatus(CompanyTypeEnum.ADVANCE.getCode(), CompanyStatusEnum.ENABLE.getCode());
		if(CollectionUtils.isNotEmpty(itbCompanyInfos)){
			itbCompanyInfo = itbCompanyInfos.get(0);
		}
		//添加代偿信息
		ItbAdvanceInfo itbAdvanceInfo = getItbAdvanceInfo(itbCompanyInfo);
		itbAdvanceInfo = save(itbAdvanceInfo);
		
		List<ItbRepayInfo> itbRepayInfos = itbRepayInfoService.getAdvanceRepayInfos();
		//获取已还金额
		Map<Long, Double> map = itbCapitalFlowService.getPaidAmount(BusinessTypeEnum.REPAY.getCode(), new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" "+endtime, RepayStatusEnum.REPAY_OVERDUE.getCode());
		
		List<ItbAdvanceRecordInfo> advanceRecordInfos = new ArrayList<ItbAdvanceRecordInfo>();
		for (ItbRepayInfo itbRepayInfo : itbRepayInfos) {
			double duetoReapyCapital = itbRepayInfo.getDuetoReapyCapital()==null?0d:itbRepayInfo.getDuetoReapyCapital();
			//代偿本金
			double advanceCapital = MathUtil.sub(duetoReapyCapital, map.get(itbRepayInfo.getId())==null?0d:map.get(itbRepayInfo.getId()));
			//代偿利息
			double advanceInterest =0d;
			
			if(advanceCapital<0||advanceInterest<0){
				throw new Exception("计算代偿金额异常;还款标的ID:+"+itbRepayInfo.getId()+"advanceCapital="+advanceCapital+"advanceInterest="+advanceInterest);
			}
			ItbAdvanceRecordInfo advanceRecordInfo = getItbAdvanceRecordInfo(itbCompanyInfo, itbAdvanceInfo,
					itbRepayInfo, advanceCapital, advanceInterest);
			advanceRecordInfos.add(advanceRecordInfo);
			itbAdvanceInfo.setCapitalAmount(MathUtil.add(itbAdvanceInfo.getCapitalAmount(), advanceRecordInfo.getCapitalAmount()));
			itbAdvanceInfo.setInterestAmount(MathUtil.add(itbAdvanceInfo.getInterestAmount(), advanceRecordInfo.getInterestAmount()));
			itbAdvanceInfo.setNum(itbAdvanceInfo.getNum()+1);
		}
		//保存信息
		save(itbAdvanceInfo);
		advanceRecordService.saveAll(advanceRecordInfos);
	}
	/**
	 * 组装代偿记录信息
	 * @param itbCompanyInfo
	 * @param itbAdvanceInfo
	 * @param itbRepayInfo
	 * @param advanceCapital
	 * @param advanceInterest
	 * @return
	 */
	private ItbAdvanceRecordInfo getItbAdvanceRecordInfo(ItbCompanyInfo itbCompanyInfo, ItbAdvanceInfo itbAdvanceInfo,
			ItbRepayInfo itbRepayInfo, double advanceCapital, double advanceInterest) {
		ItbAdvanceRecordInfo advanceRecordInfo = new ItbAdvanceRecordInfo();
		advanceRecordInfo.setAdvanceId(itbAdvanceInfo.getId());
		advanceRecordInfo.setAdvanceTime(new Date());
		advanceRecordInfo.setCapitalAmount(advanceCapital);
		advanceRecordInfo.setCompanyId(itbCompanyInfo.getId());
		advanceRecordInfo.setCreateTime(new Date());
		advanceRecordInfo.setInterestAmount(advanceInterest);
		advanceRecordInfo.setLoanUserId(itbRepayInfo.getLoanUserId());
		advanceRecordInfo.setPayNo(itbCompanyInfo.getPayId());
		advanceRecordInfo.setProjectId(itbRepayInfo.getProjectId());
		advanceRecordInfo.setRepayId(itbRepayInfo.getId());
		advanceRecordInfo.setStatus(AdvanceRecordStatusEnum.SAVE.getCode());
		return advanceRecordInfo;
	}
	/**
	 * 获取代偿信息
	 * @param itbCompanyInfo
	 * @return
	 */
	private ItbAdvanceInfo getItbAdvanceInfo(ItbCompanyInfo itbCompanyInfo) {
		ItbAdvanceInfo itbAdvanceInfo = new ItbAdvanceInfo();
		itbAdvanceInfo.setAdvanceTime(new Date());
		itbAdvanceInfo.setCapitalAmount(0d);
		itbAdvanceInfo.setCompanyId(itbCompanyInfo.getId());
		itbAdvanceInfo.setCompanyName(itbCompanyInfo.getName());
		itbAdvanceInfo.setCompanyNo(itbCompanyInfo.getCode());
		itbAdvanceInfo.setCreateTime(new Date());
		itbAdvanceInfo.setInterestAmount(0d);
		itbAdvanceInfo.setNum(0l);
		itbAdvanceInfo.setPayNo(itbCompanyInfo.getPayId());
		return itbAdvanceInfo;
	}
	
	@Override
	public Page<ItbAdvanceInfo> findPage(final ItbAdvanceInfo example, Pageable page) {
		return itbAdvanceRepository.findAll(new Specification<ItbAdvanceInfo>() {
			@Override
			public Predicate toPredicate(Root<ItbAdvanceInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> params = new ArrayList<Predicate>();
				Predicate[] predicates = new Predicate[params.size()];
				criteriaQuery.where(params.toArray(predicates));
                Path<String> createTimePath = root.get("createTime");
                criteriaQuery.orderBy(criteriaBuilder.asc(createTimePath));
				return null;
			}
		}, page);
	}
}
