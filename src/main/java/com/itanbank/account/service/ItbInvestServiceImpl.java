package com.itanbank.account.service;

import com.itanbank.account.domain.web.*;
import com.itanbank.account.domain.web.enums.OrderStatusEnum;
import com.itanbank.account.repository.web.ItbCapitalFlowInfoRepository;
import com.itanbank.account.repository.web.ItbOrderInfoRepository;
import com.itanbank.account.repository.web.ItbProjectAccountRepository;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.itanbank.account.repository.web.ItbInvestRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class ItbInvestServiceImpl implements ItbInvestService{

	@Autowired
	private ItbInvestRepository investRepository;

	@Autowired
	private ItbProjectAccountService itbProjectAccountService;

	@Autowired
	private ItbOrderInfoService itbOrderInfoService;

	@Autowired
	private ItbCapitalFlowInfoRepository itbCapitalFlowInfoRepository;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 查询投资记录
	 * @param projectId
	 * @param userId
	 * @return
	 */
	@Override
	public List<ItbInvestInfo> findByProjectIdAndUserId(Long projectId, Long userId){
		return investRepository.findByProjectIdAndUserId(projectId, userId);
	}

	/**
	 * 统计标的的投资记录
	 * @param projectId
	 * @param status
	 * @return
	 */
	@Override
	public Long countByProjectIdAndStatus(Long projectId, String status){
		return investRepository.countByProjectIdAndStatus(projectId, status);
	}

	/**
	 * 查询标的的投资记录
	 * @param projectId
	 * @param status
	 * @return
	 */
	@Override
	public List<ItbInvestInfo> findByProjectIdAndStatus(Long projectId, String status){
		return investRepository.findByProjectIdAndStatus(projectId, status);
	}

	/**
	 * 统计标中已经返款的投资记录数
	 * @param projectId
	 * @return
	 */
	@Override
	public Long countByBackUser(Long projectId){
		return investRepository.countByBackUser(projectId);
	}

	/**
	 * 查询标的的投资记录
	 * @param projectId
	 * @return
	 */
	@Override
	public List<ItbInvestInfo> findByProjectId(Long projectId){
		return investRepository.findByProjectId(projectId);
	}

	@Override
	public ItbInvestInfo findById(Long id){
		return investRepository.findOne(id);
	}
	@Override
	public ItbInvestInfo saveItbInvest(ItbInvestInfo itbInvestInfo){
		return investRepository.save(itbInvestInfo);
	}

	/**
	 * 取消超时的订单
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
	@Override
	public void cancelTimeoutOrder(ItbOrderInfo orderInfo) throws Exception{
		// 检查是否有支付成功的流水
		ItbCapitalFlowInfo capitalFlowInfo = itbCapitalFlowInfoRepository.findByOrderNo(orderInfo.getOrderNo());
		if(capitalFlowInfo != null) {
			logger.error("取消超时的订单失败,存在资金流水记录。订单信息{}", orderInfo);
			return ;
		}

		// 检查是否有冻结可投金额
		ItbProjectAccount projectAccount = itbProjectAccountService.findById(orderInfo.getProjectId());

		// 释放可投金额
		boolean isPass = itbProjectAccountService.updateCanAmountByIdAndVersion(orderInfo.getAmount(), projectAccount.getId(), projectAccount.getVersion());
		if(!isPass) {
			logger.error("取消超时的订单失败，更新标的账户表失败。订单信息{}", orderInfo);
			throw new Exception("取消超时的订单失败，更新标的账户表失败。订单信息"  + orderInfo);
		}
		isPass = itbOrderInfoService.updateOrderByIdAndVersion(orderInfo.getTradeNo(), OrderStatusEnum.FAILURE_TO_PAY.getCode(), orderInfo.getId(), orderInfo.getVersion());
		if (!isPass){
			logger.error("取消超时的订单失败，更新订单表失败。订单信息{}", orderInfo);
			throw new Exception("取消超时的订单失败，更新标的订单表失败。订单信息"  + orderInfo);
		}
	}
	
	@Override
	public Page<ItbInvestInfo> findPage(final ItbInvestInfo example, Pageable page) {
		return investRepository.findAll(new Specification<ItbInvestInfo>() {
			@Override
			public Predicate toPredicate(Root<ItbInvestInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> params = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(example.getUserName())){
					Path<String> orderNoPath = root.get("userName");
					params.add(criteriaBuilder.equal(orderNoPath, example.getUserName()));
				}
				if(example.getProjectId() != null){
					Path<Long> usrIdPath = root.get("projectId");
					params.add(criteriaBuilder.equal(usrIdPath, example.getProjectId()));
				}
				Predicate[] predicates = new Predicate[params.size()];
				criteriaQuery.where(params.toArray(predicates));

                Path<String> createTimePath = root.get("createTime");
                criteriaQuery.orderBy(criteriaBuilder.asc(createTimePath));
				return null;
			}
		}, page);
	}
	/**
	 * 查询标的应发收益
	 * @param projectId
	 * @return
	 */
	@Override
	public Double queryProjectSumInterest(Long projectId){
		return investRepository.queryProjectSumInterest(projectId);
	}
}
