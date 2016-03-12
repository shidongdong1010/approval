package com.itanbank.account.repository.web;

import com.itanbank.account.domain.web.ItbOrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by SDD on 2016/2/25.
 */
public interface ItbOrderInfoRepository extends JpaRepository<ItbOrderInfo,Long>,JpaSpecificationExecutor<ItbOrderInfo> {

    List<ItbOrderInfo> findByBusinessTypeAndOrderStatus(String businessType, String orderStatus);

    /**
     * 查询订单时间超过指定秒数的订单
     * @param businessType 业务类型
     * @param orderStatus 订单状态
     * @param timeout 超时秒数
     * @return
     */
    @Query("from ItbOrderInfo t where t.businessType = ?1 and t.orderStatus = ?2 and (now() - t.orderTime) > ?3")
    List<ItbOrderInfo> findTimeroutOrderByBusinessTypeAndOrderStatus(String businessType, String orderStatus, Double timeout);


    /**
     * 按订单号查询
     * @param orderNo
     * @return
     */
    @Query("from ItbOrderInfo t where t.orderNo = ?1")
    ItbOrderInfo findByOrderNo(String orderNo);

    /**
     * 查询个人投资者的支付中或支付完成的订单
     * @param userId
     * @param projectId
     * @param businessType
     * @return
     */
    @Query("from ItbOrderInfo t where t.userId = ?1 and t.projectId = ?2 and t.businessType = ?3 and t.orderStatus != 3")
    List<ItbOrderInfo> findPayInByUser(Long userId, Long projectId, String businessType);

    /**
     * 查询企业投资者或垫付公司的支付中或支付完成的订单
     * @param companyId
     * @param projectId
     * @param businessType
     * @return
     */
    @Query("from ItbOrderInfo t where t.userId = ?1 and t.userType = '02' and t.projectId = ?2 and t.businessType = ?3 and t.orderStatus != 3")
    List<ItbOrderInfo> findPayInByCompany(Long companyId, Long projectId, String businessType);

    @Modifying
    @Query(value = "update itb_order_info t set t.trade_no = ?1, t.order_status = ?2, t.version = t.version + 1 where t.id = ?3 and t.version = ?4", nativeQuery = true)
    int updateOrderByIdAndVersion(String tradeNo, String orderStatus, Long id, Long version);
}
