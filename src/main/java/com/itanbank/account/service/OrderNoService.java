package com.itanbank.account.service;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 得到订单号
 * Created by SDD on 2016/2/24.
 */
@Service
public class OrderNoService {

    @PersistenceContext(unitName = "webPersistenceUnit")
    private EntityManager em;

    public String getOrderNo(String prefix){
        String sql = "SELECT generate_orderNo(?, 8)";
        return em.createNativeQuery(sql).setParameter(1, prefix).getSingleResult().toString();
    }
}
