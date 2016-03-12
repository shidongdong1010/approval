package com.itanbank.account.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.itanbank.account.domain.web.ItbBackPlatformInfo;
import com.itanbank.account.repository.web.ItbBackPlatformInfoRepository;

@Service
public class ItbBackPlatformInfoServiceImpl implements ItbBackPlatformInfoService{
	@Autowired
	private  ItbBackPlatformInfoRepository itbBackPlatformInfoRepository;
	
	
	@Override
	public Page<ItbBackPlatformInfo> findPage(final ItbBackPlatformInfo example, Pageable page) {
		return itbBackPlatformInfoRepository.findAll(new Specification<ItbBackPlatformInfo>() {
			@Override
			public Predicate toPredicate(Root<ItbBackPlatformInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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
	
	@Override
	public ItbBackPlatformInfo findByProjectIdAndCompanyId(Long projectId, Long companyId) {
		return itbBackPlatformInfoRepository.findByProjectIdAndCompanyId(projectId, companyId);
	}
}
