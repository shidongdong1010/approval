package com.itanbank.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itanbank.account.domain.account.CsWithdrawDetail;
import com.itanbank.account.repository.account.CsWithdrawDetailRepository;

@Service
public class CsWithdrawDetailServiceImpl implements CsWithdrawDetailService {
	@Autowired
	private CsWithdrawDetailRepository csWithdrawDetailRepository;

	@Override
	public void saveCsWithdrawDetail(CsWithdrawDetail csWithdrawDetail) {
		csWithdrawDetailRepository.save(csWithdrawDetail);
		
	}

	@Override
	public List<CsWithdrawDetail> queryCsWithdrawDetailByBatchNo(String batchNo) {
		
		return csWithdrawDetailRepository.queryCsWithdrawDetailByBatchNo(batchNo);
	}
	
}
