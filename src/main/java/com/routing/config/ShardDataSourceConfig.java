package com.routing.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.routing.datasource.ShardDataSource;
import com.routing.datasource.ShardConfig;
import com.routing.datasource.ShardProperty;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(
		entityManagerFactoryRef="shardEntityManagerFactory",
		transactionManagerRef="shardTransactionManager",
		basePackages = "com.routing.shard.repository")
@EnableTransactionManagement
@EnableConfigurationProperties({ShardProperty.class})
public class ShardDataSourceConfig {

	@Autowired(required = false)
    private PersistenceUnitManager persistenceUnitManager;
	
	@Autowired
	private ShardProperty property;

	@Bean(name="shard")
	public DataSource dataSource() {
		ShardDataSource dataSource = new ShardDataSource();
		Map<Object, Object> param = new HashMap<Object, Object>();
		for (ShardConfig config : property.getShard()) {           
			HikariDataSource hikari = new HikariDataSource(config.getHikari()); 
			param.put(config.getKey(), hikari);
        }
		dataSource.setTargetDataSources(param);
		return dataSource;
	}
	
	@Bean(name="shardEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			final JpaProperties jpaProperties, @Qualifier("shard") DataSource dataSource) {
		AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(jpaProperties.isShowSql());
        adapter.setDatabase(jpaProperties.getDatabase());
        adapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
        adapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        
		EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(adapter,
				jpaProperties.getProperties(), this.persistenceUnitManager);
		return builder
	      .dataSource(dataSource)
	      .packages("com.routing.shard.domain")
	      .persistenceUnit("shard")
	      .build();
	}

	@Bean(name="shardTransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("shardEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
