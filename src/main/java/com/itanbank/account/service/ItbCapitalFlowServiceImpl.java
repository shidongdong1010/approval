package com.itanbank.account.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itanbank.account.domain.app.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.itanbank.account.domain.web.ItbCapitalFlowInfo;
import com.itanbank.account.repository.web.ItbCapitalFlowInfoRepository;

import javax.persistence.criteria.*;

@Service
public class ItbCapitalFlowServiceImpl implements ItbCapitalFlowService{

	@Autowired
	private ItbCapitalFlowInfoRepository capitalFlowInfoRepository;

	/**
	 * 分页查询
	 * @param example
	 * @param page
	 * @return
	 */
	@Override
	public Page<ItbCapitalFlowInfo> findPage(final ItbCapitalFlowInfo example, Pageable page){
		return capitalFlowInfoRepository.findAll(new Specification<ItbCapitalFlowInfo>() {
			@Override
			public Predicate toPredicate(Root<ItbCapitalFlowInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> params = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(example.getOrderNo())){
					Path<String> orderNoPath = root.get("orderNo");
					params.add(criteriaBuilder.equal(orderNoPath, example.getOrderNo()));
				}
				if(StringUtils.isNotBlank(example.getBusinessType())){
					Path<String> businessTypePath = root.get("businessType");
					params.add(criteriaBuilder.equal(businessTypePath, example.getBusinessType()));
				}
				if(example.getUserId() != null){
					Path<Long> usrIdPath = root.get("userId");
					params.add(criteriaBuilder.equal(usrIdPath, example.getUserId()));
				}
				if(StringUtils.isNotBlank(example.getUserType())){
					Path<String> userTypePath = root.get("userType");
					params.add(criteriaBuilder.equal(userTypePath, example.getUserType()));
				}

				Predicate[] predicates = new Predicate[params.size()];
				criteriaQuery.where(params.toArray(predicates));

                Path<String> createTimePath = root.get("createTime");
                criteriaQuery.orderBy(criteriaBuilder.asc(createTimePath));
				return null;
			}
		}, page);
	}
	
	@Override
	public ItbCapitalFlowInfo saveItbCapitalFlow(ItbCapitalFlowInfo capitalFlowInfo){
		return capitalFlowInfoRepository.save(capitalFlowInfo);
	}
	@Override
	public Map<Long, Double> getPaidAmount(String businessType,String endtime,String repayStatus){
		List<Object> maps = capitalFlowInfoRepository.getPaidAmount(businessType, endtime, repayStatus);
		Map<Long, Double> paidAmountMap = new HashMap<Long, Double>();
		for (Object map : maps) {
			Object[] obj = (Object[])map;
			paidAmountMap.put(Long.parseLong(obj[0].toString()),Double.parseDouble(obj[1].toString()));
		}
		return paidAmountMap;
	}
	
	@Override
	public ItbCapitalFlowInfo findByOrderNo(String orderNo) {
		return capitalFlowInfoRepository.findByOrderNo(orderNo);
	}
}
