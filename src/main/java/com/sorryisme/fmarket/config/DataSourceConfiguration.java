package com.sorryisme.fmarket.config;

import com.sorryisme.fmarket.common.AppConstants;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class DataSourceConfiguration {

    @Bean(name = AppConstants.DATASOURCE_SOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.source")
    public DataSource sourceDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = AppConstants.DATASOURCE_REPLICA)
    @ConfigurationProperties(prefix = "spring.datasource.replica")
    public DataSource replicaDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }


    @Bean
    public ReplicationRoutingDataSource routingDataSource(
            @Qualifier(AppConstants.DATASOURCE_SOURCE) DataSource sourceDataSource,
            @Qualifier(AppConstants.DATASOURCE_REPLICA) DataSource replicaDataSource) {

        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(AppConstants.DATASOURCE_SOURCE, sourceDataSource);
        dataSourceMap.put(AppConstants.DATASOURCE_REPLICA, replicaDataSource);

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(sourceDataSource);

        return routingDataSource;
    }

    @Bean
    @Primary
    public DataSource dataSource(ReplicationRoutingDataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

}