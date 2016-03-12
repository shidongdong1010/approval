package com.itanbank.account.web.tag;


import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.itanbank.account.domain.app.DimNode;
import com.itanbank.account.service.DimService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * 生成checkbox标签
 * @author D.D
 * @date 2014-8-11 下午02:28:46
 */
public class CheckboxTag extends BodyTagSupport {

    /**
     * 维度中的Key, 必须有值
     */
    private String key;

    /**
     * 默认选中上的值
     */
    private String value;

    /**
     * html中的id属性
     */
    private String id;
    /**
     * html中的name属性
     */
    private String name;
    /**
     * html中的class属性
     */
    private String className;
    /**
     * html中的style属性
     */
    private String style;
    /**
     * html中的data-options属性
     */
    private String dataOptions;
    /**
     * html属性集合，属性分为"属性名：属性值"，多个属性之间用逗号分隔
     * 例如："id: 1, name: 2, value: 3"
     */
    private String attributes;

    private String var;

    private DimService dimService;

    @Override
    public int doEndTag() throws JspTagException {
        try {
            if(dimService == null){
                ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
                dimService =  ctx.getBean(DimService.class);
            }
            List<DimNode> list = dimService.findDimNodeByTreeNo(key);

            StringBuilder out1 = new StringBuilder();
            // 属性集合
            if(!isNull(attributes)){
                String attributeArr[] = attributes.split(",");
                for(String attribute : attributeArr){
                    String [] attributeObj =  attribute.split(":");
                    out1.append(" "+attributeObj[0].trim()+"=\""+attributeObj[1].trim()+"\"");
                }
            }

            String valueArr[] = null;
            if(!isNull(value)){
                valueArr = value.split(",");
            }

            StringBuilder out = new StringBuilder();
            for(DimNode node : list){
                out.append("<span ");
                if(!isNull(className)){
                    out.append(" class=\""+className+"\"");
                }
                if(!isNull(style)){
                    out.append(" style=\""+style+"\"");
                }
                out.append(">");
                out.append("<input type=\"checkbox\"");
                if(valueArr != null && valueArr.length > 0){
                    for(String v : valueArr){
                        if(node.getNodeNo().equals(v)){
                            out.append(" checked");
                        }
                    }
                }
                if(!isNull(id)){
                    out.append(" id=\"" + id + node.getNodeNo() + "\"");
                }
                if(!isNull(name)){
                    out.append(" name=\""+name+"\"");
                }
                if(!isNull(dataOptions)){
                    out.append(" data-options=\""+dataOptions+"\"");
                }
                out.append(" value=\""+node.getNodeNo()+"\"");
                out.append(out1);
                out.append(" />");

                out.append("<label");
                if(!isNull(id)){
                    out.append(" for=\"" + id + node.getNodeNo() + "\"");
                }
                out.append(">"+node.getNodeName()+"</label>");
                out.append("</span>\r\n");
            }

            pageContext.getOut().write(out.toString());

            // 将数据设置到pageContext
            if(!isNull(var)){
                pageContext.setAttribute(var, list);
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

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataOptions() {
        return dataOptions;
    }

    public void setDataOptions(String dataOptions) {
        this.dataOptions = dataOptions;
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
