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

import com.itanbank.account.domain.web.VItbProjectandbackAdvance;
import com.itanbank.account.repository.web.VItbProjectandbackAdvanceRepository;

@Service
public class VItbProjectandbackAdvanceServiceImpl implements VItbProjectandbackAdvanceService {
	
	@Autowired
	private VItbProjectandbackAdvanceRepository vitbProjectandbackAdvanceRepository;
	
	
	@Override
	public VItbProjectandbackAdvance findById(Long id) {
		return vitbProjectandbackAdvanceRepository.findOne(id);
	}
	
	
	@Override
	public Page<VItbProjectandbackAdvance> findPage(final VItbProjectandbackAdvance example, Pageable page) {
		return vitbProjectandbackAdvanceRepository.findAll(new Specification<VItbProjectandbackAdvance>() {
            @Override
            public Predicate toPredicate(Root<VItbProjectandbackAdvance> root, CriteriaQuery<?> criteriaQuery,
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
                if (StringUtils.isNoneBlank(example.getStatus())) {
                    Path<String> statusPath = root.get("status");
                    params.add(criteriaBuilder.equal(statusPath, example.getStatus()));
                }
                if (StringUtils.isNoneBlank(example.getIsAdvance())) {
                    Path<String> isAdvancePath = root.get("isAdvance");
                    params.add(criteriaBuilder.equal(isAdvancePath, example.getIsAdvance()));
                }

                Predicate[] predicates = new Predicate[params.size()];
                criteriaQuery.where(params.toArray(predicates));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
                return null;
            }
        }, page);
	}

    @Override
    public List<VItbProjectandbackAdvance> findAll(){
        return vitbProjectandbackAdvanceRepository.findAll();
    }
}
