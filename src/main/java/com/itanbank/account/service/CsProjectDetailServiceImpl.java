package com.itanbank.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itanbank.account.domain.account.CsProjectDetail;
import com.itanbank.account.repository.account.CsProjectDetailRepository;

@Service
public class CsProjectDetailServiceImpl implements CsProjectDetailService {
	
	@Autowired
	private CsProjectDetailRepository csProjectDetailRepository;
	
	@Override
	public CsProjectDetail saveCsProjectDetail(CsProjectDetail csProjectDetail) {
		return csProjectDetailRepository.save(csProjectDetail);
	}

	@Override
	public List<CsProjectDetail> queryCsProjectDetailByBatchNo(String batchNo) {
		
		return csProjectDetailRepository.queryCsProjectDetailByBatchNo(batchNo);
	}
	
}
