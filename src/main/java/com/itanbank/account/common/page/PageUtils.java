package com.itanbank.account.common.page;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 分页工具类
 * Created by dongdongshi on 16/2/3.
 */
public class PageUtils {

    /**
     * 获取分页的bean
     * @param request
     * @return
     */
    public static Pageable getPageable(HttpServletRequest request){
        String pagestr = request.getParameter("page");
        Integer page = Integer.parseInt(pagestr == null ? "1" : pagestr);
        String rowsstr = request.getParameter("rows");
        Integer rows = Integer.parseInt(rowsstr == null ? "10" : rowsstr);
        String sidx = request.getParameter("sidx");
        String sord = request.getParameter("sord");

        // 获取排序的字段
        Sort sort = null;
        if(StringUtils.isNotBlank(sidx)){
            sort = new Sort(Sort.Direction.fromString(sord),  sidx);
        }
        // Pageable 创建，提供页数，
        // 注意spring data pageable是第一页是从0开始的，所以此处-1了
        Pageable pageable = new PageRequest(page - 1, rows, sort);
        return pageable;
    }

    public static JqGridPage toJqGridPage(Page<? extends Serializable> pagess){
        JqGridPage jqGridPage = new JqGridPage();
        // 注意spring data pagess是第一页是从0开始的，所以此处+1了
        jqGridPage.setPage(pagess.getNumber() + 1);
        jqGridPage.setTotal(pagess.getTotalPages());
        jqGridPage.setRecords(pagess.getTotalElements());
        jqGridPage.setRows(pagess.getContent());
        return jqGridPage;
    }
}
