package com.br.api.configs;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "m3EntityManagerFactory"
						, transactionManagerRef = "m3TransactionManager"
						, basePackages = {"com.br.api.repositories.m3" }
)
public class DbM3Config {

	@Primary
	@Bean(name = "m3Properties")
	@ConfigurationProperties("spring.datasource.m3")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
		
	}

	@Primary
	@Bean(name = "m3Datasource")
	@ConfigurationProperties(prefix = "spring.datasource.m3")
	public DataSource datasource(@Qualifier("m3Properties") DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().build();
		
	}

	@Primary
	@Bean(name = "m3EntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder,
			@Qualifier("m3Datasource") DataSource dataSource) {
		return builder.dataSource(dataSource)
				.packages("com.br.api.models.m3")
//				.persistenceUnit("Item")
				.build();
		
	}

	@Primary
	@Bean(name = "m3TransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("m3EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
		
	}

}
