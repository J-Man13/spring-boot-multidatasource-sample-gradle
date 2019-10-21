package com.fuad.muldatasource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(basePackages = "com.fuad.muldatasource.repository.users",
        entityManagerFactoryRef = "usersDbLocalContainerEntityManagerFactoryBean",
        transactionManagerRef = "usersDbPlatformTransactionManager")
public class UsersDBConfig {

    @Autowired
    private Environment environment;

    @Bean
    @ConfigurationProperties("users-conf.datasource")
    public DataSource bookingDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean usersDbLocalContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(bookingDataSource());
        em.setPackagesToScan(new String[]{"com.fuad.muldatasource.model.entities.user"});
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("users-conf.datasource.hibernate.ddl-auto"));
        properties.put("hibernate.format_sql",environment.getProperty("users-conf.datasource.hibernate.format_sql"));
        properties.put("hibernate.show_sql", environment.getProperty("users-conf.datasource.hibernate.show-sql"));
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    public PlatformTransactionManager usersDbPlatformTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(usersDbLocalContainerEntityManagerFactoryBean().getObject());
        return transactionManager;
    }
}
