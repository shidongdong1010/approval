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

import com.itanbank.account.domain.web.ItbInvestInfo;
import com.itanbank.account.domain.web.ItbUser;
import com.itanbank.account.repository.web.ItbUserRepository;

@Service
public class ItbUserServiceImpl implements ItbUserService{

	@Autowired
	private ItbUserRepository itbUserRepository;

	@Override
	public void save(ItbUser itbUser){
		itbUserRepository.save(itbUser);
	}

	@Override
	public ItbUser findById(Long id){
		return itbUserRepository.findOne(id);
	}
	
	@Override
	public Page<ItbUser> findPage(final ItbUser example, Pageable page) {
		return itbUserRepository.findAll(new Specification<ItbUser>() {
			@Override
			public Predicate toPredicate(Root<ItbUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> params = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(example.getMobile())){
					Path<String> mobilePath = root.get("mobile");
					params.add(criteriaBuilder.equal(mobilePath, example.getMobile()));
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
