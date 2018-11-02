package com.cassandra.config;

// package com.config.cassandra;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import
// org.springframework.boot.context.properties.EnableConfigurationProperties;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
// import
// org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
// import
// org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
// import org.springframework.data.cassandra.mapping.CassandraMappingContext;
// import
// org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
//
// @Configuration
// @EnableCassandraRepositories(basePackages = { "com.changhongit.tcar" })
// @EnableConfigurationProperties(CassandraBean.class)
// public class Cassandra extends AbstractCassandraConfiguration
// {
//
// @Autowired
// private CassandraBean cassandraBean;
//
// @Bean
// public CassandraClusterFactoryBean cluster()
// {
// CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
// cluster.setContactPoints(cassandraBean.getContactpoints());
// cluster.setPort(cassandraBean.getPort());
// return cluster;
// }
//
// @Override
// protected String getKeyspaceName()
// {
// return cassandraBean.getKeyspace();
// }
//
// @Bean
// public CassandraMappingContext cassandraMapping() throws
// ClassNotFoundException
// {
// return new BasicCassandraMappingContext();
// }
// }
