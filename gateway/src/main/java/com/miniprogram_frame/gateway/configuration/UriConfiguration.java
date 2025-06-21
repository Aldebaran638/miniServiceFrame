package com.miniprogram_frame.gateway.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/*
 * 笔记类型：知识学习
 * 
 * 学习对象：Spring gateway框架
 * 
 * 内容：
 * Application类的@EnableConfigurationProperties(UriConfiguration.class) 注解
 * 和这里的@ConfigurationProperties必须同时配合使用。
 * 
 */
@ConfigurationProperties
public class UriConfiguration {

  private String httpbin = "http://httpbin.org:80";

  public String getHttpbin() {
    return httpbin;
  }

  public void setHttpbin(String httpbin) {
    this.httpbin = httpbin;
  }
}
