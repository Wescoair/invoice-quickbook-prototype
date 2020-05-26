package com.tcmis.invoice.configuration.spring;

import java.sql.SQLException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.tcmis.invoice.configuration.properties.HostProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import oracle.jdbc.pool.OracleDataSource;

/**
 * Configuration file to enable Oracle database
 *
 * @author hoa.vu
 *
 */
@Configuration
public class OracleConfiguration {
	@Autowired
	private HostProperties hostProperties;

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource.hikari")
	public HikariConfig dataSourceProperties() {
	    return new HikariConfig();
	}

	@Bean
	/**
	 * Partially obtain datasource's properties from within the application, and
	 * combine them with those of external source to create the active DataSource.
	 *
	 * @return
	 * @throws SQLException
	 */
	public HikariDataSource dataSource(HikariConfig hikariConfig) throws SQLException {
		OracleDataSource oracleDataSource = new OracleDataSource();
		oracleDataSource.setURL(hostProperties.getUrl());
		oracleDataSource.setUser(hostProperties.getUsername());
		oracleDataSource.setPassword(hostProperties.getPassword());

		Properties connectionProperties = new Properties();
		connectionProperties.put("oracle.jdbc.ReadTimeout", "30000");
		oracleDataSource.setConnectionProperties(connectionProperties);

		hikariConfig.setDataSource(oracleDataSource);

		return new HikariDataSource(hikariConfig);
	}
}