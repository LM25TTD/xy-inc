package com.lm25ttd.xyinc.rest.config;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.lm25ttd.xyinc.core.utils.Utils;

/**
 * Methods and declarations for Database Settings.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
@Configuration
@PropertySource({ "classpath:config/xyinc_database.properties" })
@EnableJpaRepositories(basePackages = {
		"com.lm25ttd.xyinc.model" }, entityManagerFactoryRef = "xyincEntityManagerFactory", transactionManagerRef = "xyincTransactionManager")
@EnableTransactionManagement
public class XYIncDatabaseSettings {
	private static final Logger LOGGER = LoggerFactory.getLogger(XYIncDatabaseSettings.class);
	private static final String[] DATA_MODEL_PKG = { "com.lm25ttd.xyinc.model" };

	@Value("${xyinc.jdbc.driverClassName}")
	private String databaseDriver = null;

	@Value("${xyinc.jdbc.url}")
	private String databaseUrl = null;

	@Value("${xyinc.jdbc.user}")
	private String databaseUser = null;

	@Value("${xyinc.jdbc.pass}")
	private String databasePass = null;

	@Value("${xyinc.hibernate.dialect}")
	private String databaseDialect = null;

	@Value("${xyinc.hibernate.show_sql:false}")
	private String databaseShowSql = null;

	@Value("${xyinc.hibernate.generate_statistics:false}")
	private String databaseGenerateStatistics = null;

	@Value("${xyinc.hibernate.hbm2ddl.auto:update}")
	private String databaseAutomaticDDL = null;

	@Value("${xyinc.hibernate.hbm2ddl.import_files:}")
	private String databaseImportFiles = null;

	@Value("${xyinc.jdbc.datasource.name:}")
	private String jdbcDatasourceName = null;

	/**
	 * Set up the data source for database.
	 */
	public DataSource dataSource() {
		if (jdbcDatasourceName != null) {
			try {
				Context ic = new InitialContext();
				Context initialContext = (Context) ic.lookup("java:comp/env");
				return (DataSource) initialContext.lookup(jdbcDatasourceName);
			} catch (NamingException e) {
				e.printStackTrace();
				return getDefaultDataSource();
			}
		} else {
			return getDefaultDataSource();
		}
	}

	private DataSource getDefaultDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(this.databaseDriver);
		ds.setUrl(this.databaseUrl);
		ds.setUsername(this.databaseUser);
		ds.setPassword(this.databasePass);
		return ds;
	}

	/**
	 * Adds custom Hibernate settings.
	 */
	public Properties hibernateProperties() {

		Properties p = new Properties();
		// Required
		p.setProperty("hibernate.hbm2ddl.auto", this.databaseAutomaticDDL);
		p.setProperty("hibernate.dialect", this.databaseDialect);
		p.setProperty("hibernate.show_sql", this.databaseShowSql);
		p.setProperty("hibernate.generate_statistics", this.databaseGenerateStatistics);
		p.setProperty("hibernate.connection.charSet", "UTF-8");

		// Optional
		if (!Utils.isNullOrEmpty(this.databaseImportFiles)) {
			p.setProperty("hibernate.hbm2ddl.import_files", this.databaseImportFiles);
			p.setProperty("hibernate.hbm2ddl.import_files_sql_extractor",
					"org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor");
		}
		LOGGER.debug(p.toString());
		return p;

	}

	/**
	 * Creates the EntityManager and sets custom Hibernate settings
	 *
	 * @returns An entity manager factory
	 */
	@Bean(name = "xyincEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(this.dataSource());
		em.setPackagesToScan(DATA_MODEL_PKG);

		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(adapter);
		em.setJpaProperties(this.hibernateProperties());
		return em;

	}

	/**
	 * Delegates JPA transaction manager to Spring.
	 *
	 * @returns A transaction manager
	 */
	@Bean(name = "xyincTransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("xyincEntityManagerFactory") EntityManagerFactory emf) {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;

	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer servicePropertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
