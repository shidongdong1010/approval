package com.itanbank.account.web;

import com.itanbank.account.common.VmTemplateHelper;
import com.itanbank.account.service.DimService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SDD on 2016/2/22.
 */
@Controller
public class DimController {


    @Autowired
    private VmTemplateHelper vmTemplateHelper;

    @Value("${dimnode.js.vm.file}")
    private String dimnosJsVmFile;

    @Autowired
    private DimService dimService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/js/dim.js")
    public String dimjs(HttpServletResponse response) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("nodes", dimService.findDimNodeAll());
        String result = vmTemplateHelper.mergeTemplateIntoString(dimnosJsVmFile, map);

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/javascript;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
        return null;
    }
}
