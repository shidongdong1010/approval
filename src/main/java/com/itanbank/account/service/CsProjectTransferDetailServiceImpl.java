package com.itanbank.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itanbank.account.domain.account.CsProjectTransferDetail;
import com.itanbank.account.repository.account.CsProjectTransferDetailRepository;

@Service
public class CsProjectTransferDetailServiceImpl implements CsProjectTransferDetailService {
	
	@Autowired
	private CsProjectTransferDetailRepository csProjectTransferDetailRepository;

	@Override
	public void saveCsProjectTransferDetail(CsProjectTransferDetail csProjectTransferDetail) {
		
		csProjectTransferDetailRepository.save(csProjectTransferDetail);
	}

	@Override
	public List<CsProjectTransferDetail> queryCsProjectTransferDetailByBatchNo(String batchNo) {
		
		return csProjectTransferDetailRepository.queryCsProjectTransferDetailByBatchNo(batchNo);
	}
	
	
}
