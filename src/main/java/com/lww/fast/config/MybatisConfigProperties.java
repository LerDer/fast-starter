package com.lww.fast.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lww
 * @date 2020-07-21 11:10
 */
@ConfigurationProperties("com.fast.mybatis")
public class MybatisConfigProperties {

    /**
     * 数据库连接url 如：jdbc:mysql://127.0.0.1:3306/sparrow_manager?useUnicode=true&useSSL=false&characterEncoding=utf8
     */
    private String url;

    /**
     * 数据库用户名
     */
    private String username;

    /**
     * 数据库密码
     */
    private String password;

    /**
     * 实体类包名 如：com.lww.fast.domain
     */
    private String typeAliasesPackage;

    /**
     * dao接口包名 如：com.lww.fast.dao
     */
    private String daoPackage;

    /**
     * xml文件路径 默认：classpath*:mapper/*.xml
     */
    private String xmlLocation = "classpath*:mapper/*.xml";

    /**
     * 默认：com.mysql.cj.jdbc.Driver
     */
    private String driverClassName = "com.mysql.cj.jdbc.Driver";

    public String getDaoPackage() {
        return daoPackage;
    }

    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getXmlLocation() {
        return xmlLocation;
    }

    public void setXmlLocation(String xmlLocation) {
        this.xmlLocation = xmlLocation;
    }

    public String getTypeAliasesPackage() {
        return typeAliasesPackage;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
