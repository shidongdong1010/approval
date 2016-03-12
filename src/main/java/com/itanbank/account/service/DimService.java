package com.itanbank.account.service;

import com.itanbank.account.domain.app.DimNode;
import com.itanbank.account.domain.app.DimTree;

import java.util.List;

/**
 * Created by dongdongshi on 16/2/3.
 */
public interface DimService {
    DimNode findDimNodeById(Long id);

    void saveDimNode(DimNode dimNode);

    void updateDimNode(DimNode dimNode);

    void deleteDimNode(Long id);

    DimTree findDimTreeById(Long id);

    void saveDimTree(DimTree dimTree);

    void updateDimTree(DimTree dimTree);

    void deleteDimTree(Long id);

    List<DimNode> findDimNodeAll();

    List<DimNode> findDimNodeByTreeNo(String treeNo);

    public DimNode findDimNodeByTreeNoAndNodeNo(String treeNo, String nodeNo);
}
