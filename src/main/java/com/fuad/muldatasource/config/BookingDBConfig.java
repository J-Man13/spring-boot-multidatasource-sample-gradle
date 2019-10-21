package com.fuad.muldatasource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;


@Configuration
@EnableJpaRepositories(basePackages = "com.fuad.muldatasource.repository.booking",
                       entityManagerFactoryRef = "bookingDbLocalContainerEntityManagerFactoryBean",
                       transactionManagerRef = "bookingDbPlatformTransactionManager")
public class BookingDBConfig {

    @Autowired
    private Environment environment;

    @Bean
    @ConfigurationProperties("booking-conf.datasource")
    public DataSource bookingDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean bookingDbLocalContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(bookingDataSource());
        em.setPackagesToScan(new String[]{"com.fuad.muldatasource.model.entities.booking"});
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("booking-conf.datasource.hibernate.ddl-auto"));
        properties.put("hibernate.format_sql",environment.getProperty("booking-conf.datasource.hibernate.format_sql"));
        properties.put("hibernate.show_sql", environment.getProperty("booking-conf.datasource.hibernate.show-sql"));
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    public PlatformTransactionManager bookingDbPlatformTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(bookingDbLocalContainerEntityManagerFactoryBean().getObject());
        return transactionManager;
    }

}
