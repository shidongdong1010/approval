package com.itanbank.account.config;

import com.itanbank.account.common.ApplicationHelper;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * Spring Mvc配置
 * Created by dongdongshi on 16/1/12.
 */
@Configuration
public class SpringMvcConfiguration  {

    /**
     * 配置上传限制
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxRequestSize("4000kb");
        factory.setMaxFileSize("2000kb");
        return factory.createMultipartConfig();
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return new CustomizationBean();
    }

    @Bean
    public ApplicationHelper applicationHelper(){
        return new ApplicationHelper();
    }
}
