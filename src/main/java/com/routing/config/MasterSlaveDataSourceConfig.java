package com.routing.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.routing.datasource.DatabaseRole;
import com.routing.datasource.MasterSlaveDataSource;
import com.routing.datasource.MasterSlaveProperty;
import com.routing.datasource.SlaveConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(
		entityManagerFactoryRef="entityManagerFactory",
		transactionManagerRef="transactionManager",
		basePackages = "com.routing.repository")
@EnableTransactionManagement
@EnableConfigurationProperties({MasterSlaveProperty.class})
public class MasterSlaveDataSourceConfig {

	@Autowired(required = false)
    private PersistenceUnitManager persistenceUnitManager;
	
	@Autowired
	private MasterSlaveProperty property;
	
	@Primary
	@Bean
    @ConfigurationProperties("spring.jpa")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

	@Primary
	@Bean(name="main")
	public DataSource dataSource() {
		MasterSlaveDataSource dataSource = new MasterSlaveDataSource();
		HikariDataSource master = new HikariDataSource(property.getMaster());
		dataSource.setDefaultTargetDataSource(master);
		Map<Object, Object> param = new HashMap<Object, Object>();
		for (SlaveConfig config : property.getSlave()) {           
			HikariDataSource slave = new HikariDataSource(config.getHikari()); 
			param.put(DatabaseRole.values()[config.getKey()], slave);
        }
		dataSource.setTargetDataSources(param);
		return dataSource;
	}
	
	@Primary
	@Bean(name="entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			final JpaProperties jpaProperties, @Qualifier("main") DataSource dataSource) {
		AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(jpaProperties.isShowSql());
        adapter.setDatabase(jpaProperties.getDatabase());
        adapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
        adapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        
		EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(adapter,
				jpaProperties.getProperties(), this.persistenceUnitManager);
		return builder
	      .dataSource(dataSource)
	      .packages("com.routing.domain")
	      .persistenceUnit("main")
	      .build();
	}

	@Primary
	@Bean(name="transactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
