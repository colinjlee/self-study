package com.cjl.springsecurity.config;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages="com.cjl.springsecurity")
@PropertySource("classpath:persistence-mysql.properties")
public class AppConfig {
	
	// Hold the properties
	@Autowired
	private Environment env;
	
	private Logger logger = Logger.getLogger(getClass().getName());

	// Define bean for view resolver
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	}
	
	// Define bean for security data source
	@Bean
	public DataSource securityDataSource() {
		
		// Create connection pool
		ComboPooledDataSource securityDataSource = new ComboPooledDataSource();
		
		// Property variables
		String user = env.getProperty("jdbc.user");
		String pass = env.getProperty("jdbc.password");
		String jdbcUrl = env.getProperty("jdbc.url");
		
		int initialPoolSize = Integer.valueOf(env.getProperty("connection.pool.initialPoolSize"));
		int minPoolSize = Integer.valueOf(env.getProperty("connection.pool.minPoolSize"));
		int maxPoolSize = Integer.valueOf(env.getProperty("connection.pool.maxPoolSize"));
		int maxIdleTime = Integer.valueOf(env.getProperty("connection.pool.maxIdleTime"));
		
		// Set jdbc driver class
		try {
			securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}
		
		// Log connection properties
		logger.info(">> Logging... jdbc.url = " + jdbcUrl);
		logger.info(">> Logging... jdbc.user = " + user);
		
		// Set database connection properties
		securityDataSource.setJdbcUrl(jdbcUrl);
		securityDataSource.setUser(user);
		securityDataSource.setPassword(pass);
		
		// Set connection pool properties
		securityDataSource.setInitialPoolSize(initialPoolSize);
		securityDataSource.setMinPoolSize(minPoolSize);
		securityDataSource.setMaxPoolSize(maxPoolSize);
		securityDataSource.setMaxIdleTime(maxIdleTime);
		
		return securityDataSource;
	}
	
	private Properties getHibernateProperties() {
		Properties props = new Properties();
		
		props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		
		return props;
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		
		sessionFactory.setDataSource(securityDataSource());
		sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
		sessionFactory.setHibernateProperties(getHibernateProperties());
		
		return sessionFactory;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		
		return txManager;
	}
}
