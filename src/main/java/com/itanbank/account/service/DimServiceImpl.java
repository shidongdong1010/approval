package com.itanbank.account.service;

import com.itanbank.account.domain.app.DimNode;
import com.itanbank.account.domain.app.DimTree;
import com.itanbank.account.repository.app.DimNodeRepository;
import com.itanbank.account.repository.app.DimTreeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典Service
 * Created by dongdongshi on 16/2/3.
 */
@Service
public class DimServiceImpl implements DimService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DimNodeRepository dimNodeRepository;
    @Autowired
    private DimTreeRepository dimTreeRepository;

    @Override
    public DimNode findDimNodeById(Long id) {
        return dimNodeRepository.findOne(id);
    }

    @Override
    public void saveDimNode(DimNode dimNode){
        dimNodeRepository.save(dimNode);
    }

    @Override
    public void updateDimNode(DimNode dimNode){
        dimNodeRepository.save(dimNode);
    }

    @Override
    public void deleteDimNode(Long id) {
        dimNodeRepository.delete(id);
    }

    @Override
    public DimTree findDimTreeById(Long id) {
        return dimTreeRepository.findOne(id);
    }

    @Override
    public void saveDimTree(DimTree dimTree){
        dimTreeRepository.save(dimTree);
    }

    @Override
    public void updateDimTree(DimTree dimTree){
        dimTreeRepository.save(dimTree);
    }

    @Override
    public void deleteDimTree(Long id) {
        dimTreeRepository.delete(id);
    }

    @Override
    public List<DimNode> findDimNodeAll(){
        return dimNodeRepository.findAll();
    }

    @Override
    public List<DimNode> findDimNodeByTreeNo(String treeNo){
        return dimNodeRepository.findByTreeNo(treeNo);
    }

    @Override
    public DimNode findDimNodeByTreeNoAndNodeNo(String treeNo, String nodeNo){
        return dimNodeRepository.findByTreeNoAndNodeNo(treeNo, nodeNo);
    }

}
