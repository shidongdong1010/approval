package com.itanbank.account.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.epbcommons.transformation.amount.AmountUtils;
import org.epbcommons.transformation.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.itanbank.account.domain.web.ItbCompanyInfo;
import com.itanbank.account.pay.model.query.EnterpriseRequest;
import com.itanbank.account.pay.model.query.EnterpriseResult;
import com.itanbank.account.pay.service.QueryPtpMerService;
import com.itanbank.account.repository.web.ItbCompanyInfoRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItbCompanyInfoServiceImpl implements ItbCompanyInfoService{
	
	@Autowired
	private ItbCompanyInfoRepository companyInfoRepository;
	
	@Autowired
	private QueryPtpMerService queryPtpMerService;

	@Override
	public ItbCompanyInfo findById(Long id){
		return companyInfoRepository.findOne(id);
	}

	@Override
	public ItbCompanyInfo findByPayId(String payId){
		return companyInfoRepository.findByPayId(payId);
	}

	@Override
	public Page<ItbCompanyInfo> findPage(ItbCompanyInfo example, Pageable page) {
		 return companyInfoRepository.findAll(new Specification<ItbCompanyInfo>() {
	            @Override
	            public Predicate toPredicate(Root<ItbCompanyInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	                List<Predicate> params = new ArrayList<Predicate>();
	                Predicate[] predicates = new Predicate[params.size()];
	                criteriaQuery.where(params.toArray(predicates));
	                return null;
	            }
	        }, page);
	}
	
	@Override
	public List<ItbCompanyInfo> findByTypeAndStatus(String type,String status){
		return companyInfoRepository.findByTypeAndStatus(type,status);
	}
	/***
	 * 更新企业账户信息
	 * @param amount
	 * @param id
	 * @param version
	 * @return
	 */
	@Override
	public boolean updateBalanceByIdAndVersion(Double amount, Long id, Long version){
		int result = companyInfoRepository.updateBalanceByIdAndVersion(amount, id, version);
		if(result != 1){
			return false;
		}
		return true;
	}
	
	/**
	 *查询企业账户余额
	 */
	@Override
	public Double queryCompanyAmount(Long id) throws Exception {
		//根据ID查询出企业账户信息
		ItbCompanyInfo  itbCompanyInfo = companyInfoRepository.findOne(id);
		if(null != itbCompanyInfo){
			//组装第三方查询模板
			EnterpriseRequest enterpriseRequest = new EnterpriseRequest();
			enterpriseRequest.setQuery_mer_id(itbCompanyInfo.getPayId());
			EnterpriseResult  enterpriseResult = queryPtpMerService.enterprise(enterpriseRequest);
			String  balance = enterpriseResult.getBalance();//获取第三方返回金额（分）
			if(!StringUtil.isEmpty(balance)){
				String  newBalance = AmountUtils.changeF2Y(balance);
				return  Double.parseDouble(newBalance);
			}
		}
		return 0.0;
	}
	@Override
	public ItbCompanyInfo saveItbCompanyInfo(ItbCompanyInfo companyInfo){
		return companyInfoRepository.save(companyInfo);
	}
}
