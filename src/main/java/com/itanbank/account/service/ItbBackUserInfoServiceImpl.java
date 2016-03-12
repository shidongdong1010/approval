package com.itanbank.account.service;

import com.itanbank.account.domain.web.ItbBackUserInfo;
import com.itanbank.account.repository.web.ItbBackUserInfoRepository;
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
public class ItbBackUserInfoServiceImpl implements ItbBackUserInfoService{

	@Autowired
	private ItbBackUserInfoRepository itbBackUserInfoRepository;

	/**
	 * 统计标的的返款记录
	 * @param projectId
	 * @param status
	 * @return
	 */
	@Override
	public Long countByProjectIdAndStatus(Long projectId, String status){
		return itbBackUserInfoRepository.countByProjectIdAndStatus(projectId, status);
	}

	/**
	 * 查询标的的返款记录
	 * @param projectId
	 * @param status
	 * @return
	 */
	@Override
	public List<ItbBackUserInfo> findByProjectIdAndStatus(Long projectId, String status){
		return itbBackUserInfoRepository.findByProjectIdAndStatus(projectId, status);
	}

	/**
	 * 查询标的的返款记录
	 * @param projectId
	 * @return
	 */
	@Override
	public List<ItbBackUserInfo> findByProjectId(Long projectId){
		return itbBackUserInfoRepository.findByProjectId(projectId);
	}
	
	@Override
	public Page<ItbBackUserInfo> findPage(final ItbBackUserInfo example, Pageable page) {
		return itbBackUserInfoRepository.findAll(new Specification<ItbBackUserInfo>() {
			@Override
			public Predicate toPredicate(Root<ItbBackUserInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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
