package com.itanbank.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itanbank.account.domain.account.CsRechargeDetail;
import com.itanbank.account.repository.account.CsRechargeDetailRepository;

@Service
public class CsRechargeDetailServiceImpl implements CsRechargeDetailService {
	
	@Autowired
	private CsRechargeDetailRepository csRechargeDetailRepository;

	@Override
	public void saveCsRechargeDetail(CsRechargeDetail csRechargeDetail) {
		csRechargeDetailRepository.save(csRechargeDetail);
	}

	@Override
	public List<CsRechargeDetail> queryCsRechargeDetailByBatchNo(String batchNo) {
		return csRechargeDetailRepository.queryCsRechargeDetailByBatchNo(batchNo);
	}
	
	
}
