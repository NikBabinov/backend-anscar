package ru.anscar.nikbabinov.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import ru.anscar.nikbabinov.util.EnvironmentVariables;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "ru.anscar.nikbabinov")
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {

        String dbUrl = EnvironmentVariables.getDbUrl();
        String userName = EnvironmentVariables.getDbUsername();
        String password = EnvironmentVariables.getDbPassword();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan("ru.anscar.nikbabinov");
        entityManagerFactoryBean.setJpaVendorAdapter(createVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(createJpaProperties());
        return entityManagerFactoryBean;
    }

    private HibernateJpaVendorAdapter createVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    private Properties createJpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        return properties;
    }


    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
