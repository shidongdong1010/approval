package com.itanbank.account.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.itanbank.account.domain.web.ItbAddAmountInfo;
import com.itanbank.account.domain.web.ItbBackUserInfo;
import com.itanbank.account.repository.web.ItbAddAmountInfoRepository;

/**
 * 贴现记录
 * @author wn
 *
 */
@Service
public class ItbAddAmountInfoServiceImpl implements ItbAddAmountInfoService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ItbAddAmountInfoRepository addAmountInfoRepository;
	
	/**
	 * 保存对象
	 * @param addAmountInfo
	 * @return
	 */
	@Override
	public ItbAddAmountInfo save(ItbAddAmountInfo addAmountInfo){
		return addAmountInfoRepository.save(addAmountInfo);
	}
	
	@Override
	public Page<ItbAddAmountInfo> findPage(final ItbAddAmountInfo example, Pageable page) {
		return addAmountInfoRepository.findAll(new Specification<ItbAddAmountInfo>() {
			@Override
			public Predicate toPredicate(Root<ItbAddAmountInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> params = new ArrayList<Predicate>();
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
}
