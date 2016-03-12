package com.itanbank.account.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef="entityManagerFactoryApp",transactionManagerRef="transactionManagerApp",basePackages= { "com.itanbank.account.repository.app" })//设置dao（repo）所在位置
public class RepositoryAppConfig {
    @Autowired
    private JpaProperties jpaProperties;

    @Autowired @Qualifier("appDS")
    private DataSource dataSource;

    @Bean(name = "entityManagerApp")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryApp(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactoryApp")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryApp (EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .properties(getVendorProperties(dataSource))
                .packages("com.itanbank.account.domain.app") //设置实体类所在位置
                .persistenceUnit("appPersistenceUnit")
                .build();
    }

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }

    @Bean(name = "transactionManagerApp")
    PlatformTransactionManager transactionManagerApp(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryApp(builder).getObject());
    }

}
