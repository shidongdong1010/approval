package com.itanbank.account.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.itanbank.account.domain.web.VItbProjectandbackPlatform;
import com.itanbank.account.repository.web.VItbProjectandbackPlatformRepository;

@Service
public class VItbProjectandbackPlatformServiceImpl implements VItbProjectandbackPlatformService{
	
	@Autowired
	private  VItbProjectandbackPlatformRepository  vitbProjectandbackPlatformRepository;
	@Override
	public VItbProjectandbackPlatform findById(Long id) {
		return vitbProjectandbackPlatformRepository.findOne(id);
	}
	
	@Override
	public Page<VItbProjectandbackPlatform> findPage(final VItbProjectandbackPlatform example, Pageable page) {
		return vitbProjectandbackPlatformRepository.findAll(new Specification<VItbProjectandbackPlatform>() {
            @Override
            public Predicate toPredicate(Root<VItbProjectandbackPlatform> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> params = new ArrayList<Predicate>();
                if (StringUtils.isNoneBlank(example.getLoanContractNo())) {
                    Path<String> loanContractNoPath = root.get("loanContractNo");
                    params.add(criteriaBuilder.equal(loanContractNoPath, example.getLoanContractNo()));
                }
                if (StringUtils.isNoneBlank(example.getProjectName())) {
                    Path<String> projectNamePath = root.get("projectName");
                    params.add(criteriaBuilder.equal(projectNamePath, example.getProjectName()));
                }
                Predicate[] predicates = new Predicate[params.size()];
                criteriaQuery.where(params.toArray(predicates));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
                return null;
            }
        }, page);
	}
}
