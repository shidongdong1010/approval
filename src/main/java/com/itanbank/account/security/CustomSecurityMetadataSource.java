package com.itanbank.account.security;

import com.itanbank.account.domain.app.Resource;
import com.itanbank.account.domain.app.Role;
import com.itanbank.account.service.ResourceService;
import com.itanbank.account.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.*;

/**
 * 从数据库中查询出资源和权限（角色），并将它们的关系对应起来
 * @author SDD
 *
 */
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private RoleService roleService;
    private ResourceService resourceService;
    private AntPathMatcher urlMatcher = new AntPathMatcher();

    /* 保存资源和权限的对应关系  key-资源url  value-权限 */
    private Map<String,Collection<ConfigAttribute>> relationMap = null;

    public CustomSecurityMetadataSource(RoleService roleService, ResourceService resourceService) {
        logger.info("执行 AppSecurityMetadataSource 构造方法");
        this.roleService = roleService;
        this.resourceService = resourceService;
        loadResourceAndRoleRelation();
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * 根据请求的url,获取访问该url所需的权限（角色）
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object obj)
            throws IllegalArgumentException {
        //获取请求的url地址
        String requestUrl = ((FilterInvocation)obj).getRequestUrl();
        logger.debug("请求的 requestUrl :" + requestUrl);
        Iterator<String> it = relationMap.keySet().iterator();
        while(it.hasNext()) {
            String url = it.next();
            logger.debug("配置的 url :" + url);
            if(requestUrl.indexOf("?")!=-1) {
                requestUrl = requestUrl.substring(0, requestUrl.indexOf("?"));
            }
            logger.debug("hey man :" + url);
            if(urlMatcher.match(url, requestUrl)) {
                logger.debug("已匹配 :"+url);
                return relationMap.get(url);
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

    /**
     * 加载资源和角色的对应关系
     */
    private void loadResourceAndRoleRelation() {
        relationMap = new HashMap<String,Collection<ConfigAttribute>>();
        //查出所有角色
        List<Role> roles = roleService.findAll();
        if(roles != null) {
            for(Role role : roles) {
                //查出某个角色可以访问的资源
                List<Resource> resources = resourceService.findByRoleId(role.getId());
                if(resources != null) {
                    for(Resource resource : resources) {
                        Collection<ConfigAttribute> configAttributes = null;
                        ConfigAttribute configAttribute = new SecurityConfig(role.getCode());
                        if(relationMap.containsKey(resource.getResourceContent())){
                            configAttributes = relationMap.get(resource.getResourceContent());
                            configAttributes.add(configAttribute);
                        }else{
                            configAttributes = new ArrayList<ConfigAttribute>() ;
                            configAttributes.add(configAttribute);
                            relationMap.put(resource.getResourceContent(), configAttributes);
                        }
                    }
                }
            }
        }
    }

}
