package com.itanbank.account.web.tag;

import com.itanbank.account.domain.app.DimNode;
import com.itanbank.account.service.DimService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * 将字典值翻译成中文标签
 * @author D.D
 * @date 2014-8-11 下午02:28:46
 */
public class ToCnTag extends BodyTagSupport {

    /**
     * 维度中的Key, 必须有值
     */
    private String key;

    /**
     * 值
     */
    private String value;

    private String var;

    private DimService dimService;

    @Override
    public int doEndTag() throws JspTagException {
        try {
            if(dimService == null){
                ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
                dimService =  ctx.getBean(DimService.class);
            }
            DimNode dimNode = dimService.findDimNodeByTreeNoAndNodeNo(key, value);

            StringBuilder out = new StringBuilder();
            out.append(dimNode.getNodeName());
            pageContext.getOut().write(out.toString());

            // 将数据设置到pageContext
            if(!isNull(var)){
                pageContext.setAttribute(var, dimNode);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }

    private boolean isNull(String obj){
        if(obj != null && !"".equals(obj)){
            return false;
        }
        return true;
    }


    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public DimService getDimService() {
        return dimService;
    }

    public void setDimService(DimService dimService) {
        this.dimService = dimService;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
