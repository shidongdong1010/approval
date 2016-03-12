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
@EnableJpaRepositories(entityManagerFactoryRef="entityManagerFactoryAccount",transactionManagerRef="transactionManagerAccount",basePackages= { "com.itanbank.account.repository.account" })
public class RepositoryAccountConfig {
    @Autowired
    private JpaProperties jpaProperties;

    @Autowired @Qualifier("accountDS")
    private DataSource dataSource;

    @Bean(name = "entityManagerAccount")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryAccount(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactoryAccount")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryAccount(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .properties(getVendorProperties(dataSource))
                .packages("com.itanbank.account.domain.account")
                .persistenceUnit("accountPersistenceUnit")
                .build();
    }

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }

    @Bean(name = "transactionManagerAccount")
    PlatformTransactionManager transactionManagerAccount(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryAccount(builder).getObject());
    }

}