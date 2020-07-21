package com.lww.fast.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

/**
 * @author lww
 * @date 2019-08-14 10:46
 */
@Slf4j
@Configuration
//sqlSessionTemplateRef : Usually this is only needed when you have more than one datasource.
//@MapperScan(basePackages = "com.ler.sparrowmanager.dao")
//@ConditionalOnExpression("#{environment.getProperty('com.fast.mybatis.url') != null}")
@EnableConfigurationProperties(MybatisConfigProperties.class)
public class MybatisConfig {

    @javax.annotation.Resource
    private MybatisConfigProperties config;

    @Bean("dataSource")
    public DataSource dataSource() {
        Assert.isTrue(StringUtils.isNotBlank(config.getPassword()), "数据库密码为空！");
        Assert.isTrue(StringUtils.isNotBlank(config.getUsername()), "数据库用户名为空！");
        Assert.isTrue(StringUtils.isNotBlank(config.getUrl()), "数据库链接为空！");
        Assert.isTrue(StringUtils.isNotBlank(config.getTypeAliasesPackage()), "别名包为空！");
        try {
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDriverClassName(config.getDriverClassName());
            dataSource.setUrl(config.getUrl());
            dataSource.setUsername(config.getUsername());
            dataSource.setPassword(config.getPassword());

            dataSource.setInitialSize(1);
            dataSource.setMaxActive(20);
            dataSource.setMinIdle(1);
            dataSource.setMaxWait(60_000);
            dataSource.setPoolPreparedStatements(true);
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
            dataSource.setTimeBetweenEvictionRunsMillis(60_000);
            dataSource.setMinEvictableIdleTimeMillis(300_000);
            dataSource.setValidationQuery("SELECT 1");
            return dataSource;
        } catch (Throwable throwable) {
            log.error("ex caught", throwable);
            throw new RuntimeException();
        }
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setVfs(SpringBootVFS.class);
        factoryBean.setTypeAliasesPackage(config.getTypeAliasesPackage());

        Resource[] mapperResources = new PathMatchingResourcePatternResolver().getResources(config.getXmlLocation());
        factoryBean.setMapperLocations(mapperResources);

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        //分页
        configuration.addInterceptor(new PaginationInterceptor());
        //打印sql
        configuration.addInterceptor(new PerformanceInterceptor());

        configuration.setUseGeneratedKeys(true);
        factoryBean.setConfiguration(configuration);
        return factoryBean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager platformTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "transactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("transactionManager") PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

}
