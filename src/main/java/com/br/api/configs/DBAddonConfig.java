package com.br.api.configs;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "addonEntityManagerFactory"
						, transactionManagerRef = "addonTransactionManager"
						, basePackages = {"com.br.api.repositories.addon" }
)
public class DBAddonConfig {

	@Bean(name = "addonProperties")
	@ConfigurationProperties("spring.datasource.addon")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
		
	}

	@Bean(name = "addonDatasource")
	@ConfigurationProperties(prefix = "spring.datasource.addon")
	public DataSource datasource(@Qualifier("addonProperties") DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().build();
		
	}

	@Bean(name = "addonEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder,
			@Qualifier("addonDatasource") DataSource dataSource) {
		return builder.dataSource(dataSource)
				.packages("com.br.api.models.addon")
//				.persistenceUnit("User")
//				.persistenceUnit("Product")
				.build();
		
	}

	@Bean(name = "addonTransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("addonEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
		
	}

}
