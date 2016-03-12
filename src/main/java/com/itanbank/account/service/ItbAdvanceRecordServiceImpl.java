package com.itanbank.account.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.itanbank.account.domain.web.ItbAdvanceRecordInfo;
import com.itanbank.account.repository.web.ItbAdvanceRecordRepository;

/**
 * 偿付记录
 * @author wn
 *
 */
@Service
public class ItbAdvanceRecordServiceImpl implements ItbAdvanceRecordService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ItbAdvanceRecordRepository itbAdvanceRecordRepository;
	
	@Override
	public ItbAdvanceRecordInfo findOne(Long id){
		return itbAdvanceRecordRepository.findOne(id);
	}
	/**
	 * 保存对象
	 * @param advanceInfo
	 * @return
	 */
	@Override
	public ItbAdvanceRecordInfo save(ItbAdvanceRecordInfo advanceRecordInfo){
		return itbAdvanceRecordRepository.save(advanceRecordInfo);
	}
	/**
	 * 保存对象集合
	 * @param advanceRecordInfos
	 * @return
	 */
	@Override
	public List<ItbAdvanceRecordInfo> saveAll(List<ItbAdvanceRecordInfo> advanceRecordInfos){
		return itbAdvanceRecordRepository.save(advanceRecordInfos);
	}
	
	@Override
	public Page<ItbAdvanceRecordInfo> findPage(final ItbAdvanceRecordInfo example, Pageable page) {
		return itbAdvanceRecordRepository.findAll(new Specification<ItbAdvanceRecordInfo>() {
            @Override
            public Predicate toPredicate(Root<ItbAdvanceRecordInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> params = new ArrayList<Predicate>();
                if(StringUtils.isNotBlank(example.getStatus())){
                    Path<String> usernamePath = root.get("status");
                    params.add(criteriaBuilder.equal(usernamePath,example.getStatus()));
                }
                if(null != example.getProjectId()){
                    Path<String> usernamePath = root.get("projectId");
                    params.add(criteriaBuilder.equal(usernamePath,example.getStatus()));
                }
                Predicate[] predicates = new Predicate[params.size()];
                criteriaQuery.where(params.toArray(predicates));
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
                return null;
            }
        }, page);
	}
	@Override
	public List<ItbAdvanceRecordInfo> findByProjectId(Long projectId){
		return itbAdvanceRecordRepository.findByProjectId(projectId);
	}
	@Override
	public List<ItbAdvanceRecordInfo> findByAdvanceId(Long advanceId){
		return itbAdvanceRecordRepository.findByAdvanceId(advanceId);
	}
	@Override
	public void deleteInBatch(List<ItbAdvanceRecordInfo> entities){
		itbAdvanceRecordRepository.deleteInBatch(entities);
	}
}
