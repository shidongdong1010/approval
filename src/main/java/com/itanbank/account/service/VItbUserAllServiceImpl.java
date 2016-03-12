package com.itanbank.account.service;

import com.itanbank.account.domain.web.VItbUserAll;
import com.itanbank.account.repository.web.VItbUserAllRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SDD on 2016/3/2.
 */
@Service
public class VItbUserAllServiceImpl implements VItbUserAllService {

    @Autowired
    private VItbUserAllRepository vItbUserAllRepository;

    @Override
    public VItbUserAll findById(Long id) {
        return vItbUserAllRepository.findOne(id);
    }
}
