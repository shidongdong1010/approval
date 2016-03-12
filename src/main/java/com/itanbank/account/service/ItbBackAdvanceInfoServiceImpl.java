package com.itanbank.account.service;

import com.itanbank.account.domain.web.ItbBackAdvanceInfo;
import com.itanbank.account.repository.web.ItbBackAdvanceInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 返款到投资人记录
 * @author wn
 *
 */
@Service
public class ItbBackAdvanceInfoServiceImpl implements ItbBackAdvanceInfoService{

	@Autowired
	private ItbBackAdvanceInfoRepository itbBackAdvanceInfoRepository;

	/**
	 * 根据标的ID和垫付公司查询
	 * @param projectId
	 * @param companyId
	 * @return
	 */
	@Override
	public ItbBackAdvanceInfo findByProjectIdAndCompanyId(Long projectId, Long companyId){
		return itbBackAdvanceInfoRepository.findByProjectIdAndCompanyId(projectId, companyId);
	}
	
	@Override
	public Page<ItbBackAdvanceInfo> findPage(final ItbBackAdvanceInfo example, Pageable page) {
		return itbBackAdvanceInfoRepository.findAll(new Specification<ItbBackAdvanceInfo>() {
			@Override
			public Predicate toPredicate(Root<ItbBackAdvanceInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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
