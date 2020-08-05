package com.example.hibernatefilter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    private final Environment environment;

    @Autowired
    public HibernateConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean(name = "entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory() {
        return new LocalSessionFactoryBean() {{
            setDataSource(dataSource());
            setPackagesToScan("com.example.hibernatefilter.dao.entity");
            setHibernateProperties(hibernateProperties());
        }};
    }

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource() {{
            setDriverClassName(Objects.requireNonNull(environment.getProperty("spring.datasource.driver-class-name")));
            setUrl(environment.getProperty("spring.datasource.url"));
            setUsername(environment.getProperty("spring.datasource.username"));
            setPassword(environment.getProperty("spring.datasource.password"));
        }};
    }

    private Properties hibernateProperties() {
        return new Properties() {{
            setProperty("hibernate.hbm2ddl.auto", "create-drop");
            setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            setProperty("show_sql", "true");
            setProperty("format_sql", "true");
            setProperty("use_sql_comments", "true");
        }};
    }
}