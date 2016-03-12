package com.itanbank.account.config;

import com.itanbank.account.common.EncodingUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * 国际化信息配置
 * Created by dongdongshi on 16/1/15.
 */
@Configuration
public class MessageSourceConfiguration {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.CHINESE);
        return resolver;
    }


    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // 国际化信息所在的文件名
        messageSource.setBasename("classpath:locale/messages");
        messageSource.setDefaultEncoding(EncodingUtils.UTF_8);
        messageSource.setCacheSeconds(0);
        // 如果在国际化资源文件中找不到对应代码的信息，就用这个代码作为名称
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }
}
