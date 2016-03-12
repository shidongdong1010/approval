package com.itanbank.account.repository.app;

import com.itanbank.account.domain.app.DimNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * Created by dongdongshi on 16/2/3.
 */
public interface DimNodeRepository extends JpaRepository<DimNode, Long> {

    List<DimNode> findByTreeNo(String treeNo);

    DimNode findByTreeNoAndNodeNo(String treeNo, String nodeNo);

}
