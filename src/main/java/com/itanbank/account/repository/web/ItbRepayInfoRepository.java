package com.itanbank.account.repository.web;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.itanbank.account.domain.web.ItbRepayInfo;

public interface ItbRepayInfoRepository extends JpaRepository<ItbRepayInfo,Long>,JpaSpecificationExecutor<ItbRepayInfo> {
	ItbRepayInfo  findByProjectId(Long projectId);
	
	@Query("select u from ItbRepayInfo u where  u.duetoRepayDate <SYSDATE()  and u.status in('0','2','3')")
	List<ItbRepayInfo> queryOverdueInfo();
	
	/**
	 * 查询待偿付的还款信息
	 * @return
	 * @throws Exception
	 */
    @Query("from ItbRepayInfo t where t.status=?1 ")
	List<ItbRepayInfo> getAdvanceRepayInfos(String status) throws Exception;
}
