package com.itanbank.account.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "appDS") @Qualifier("appDS")
    @Primary
    @ConfigurationProperties(prefix = "app.datasource")
    public DataSource appDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "webDS")  @Qualifier("webDS")
    @ConfigurationProperties(prefix = "web.datasource")
    public DataSource webDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "accountDS") @Qualifier("accountDS")
    @ConfigurationProperties(prefix = "account.datasource")
    public DataSource accountDataSource() {
        return DataSourceBuilder.create().build();
    }

}
