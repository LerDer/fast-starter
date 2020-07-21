package com.lww.fast.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 在其他项目引入该Starter，在properties文件中配置的时候的前缀
 *
 * @author lww
 * @date 2019-09-05 00:37
 */
@ConfigurationProperties("com.fast")
public class ConEnvProperties {

    /**
     * 当前环境，默认 local
     */
    private String env = "local";

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public Boolean islocalOrDaily() {
        return "local".equalsIgnoreCase(this.getEnv()) || "daily".equalsIgnoreCase(this.getEnv());
    }

    public Boolean isGray() {
        return "gray".equalsIgnoreCase(this.getEnv());
    }

    public Boolean isOnline() {
        return "online".equalsIgnoreCase(this.getEnv());
    }

}
