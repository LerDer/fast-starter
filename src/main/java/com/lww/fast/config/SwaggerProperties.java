package com.lww.fast.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lww
 */
@ConfigurationProperties("com.fast.swagger")
public class SwaggerProperties {

	/**
	 * controller 包名 如：com.lww.fast.controller
	 */
	private String controllerPackageName;

	/**
	 * swagger 标题
	 */
	private String title;

	/**
	 * api 版本
	 */
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getControllerPackageName() {
		return controllerPackageName;
	}

	public void setControllerPackageName(String controllerPackageName) {
		this.controllerPackageName = controllerPackageName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
