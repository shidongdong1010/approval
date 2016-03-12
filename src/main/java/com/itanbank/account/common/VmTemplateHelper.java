package com.itanbank.account.common;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.Map;

/**
 * 将模板转换成文本
 * @author SDD
 *
 */
@Service
public class VmTemplateHelper{

    private static String tplFolder = "templates/";

    @Autowired
    private VelocityEngine velocityEngine;

    public String mergeTemplateIntoString(String vm, Map<String, Object> map) {
        String tpl = tplFolder + vm;
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, tpl, "utf-8", map);

    }
}

