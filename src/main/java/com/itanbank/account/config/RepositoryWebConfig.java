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
@EnableJpaRepositories(entityManagerFactoryRef="entityManagerFactoryWeb",transactionManagerRef="transactionManagerWeb",basePackages= { "com.itanbank.account.repository.web" })
public class RepositoryWebConfig {
    @Autowired
    private JpaProperties jpaProperties;

    @Autowired @Qualifier("webDS")
    private DataSource dataSource;

    @Bean(name = "entityManagerWeb")
    @Primary
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryWeb(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactoryWeb")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryWeb(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .properties(getVendorProperties(dataSource))
                .packages("com.itanbank.account.domain.web")
                .persistenceUnit("webPersistenceUnit")
                .build();
    }

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }

    @Bean(name = "transactionManagerWeb")
    @Primary
    PlatformTransactionManager transactionManagerWeb(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryWeb(builder).getObject());
    }

}