package com.miniprogram_frame.gateway.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import com.miniprogram_frame.gateway.configuration.UriConfiguration;

/*
 * 笔记类型：知识学习
 * 学习对象：Spring gateway框架
 * 
 * 简单来说，@SpringBootApplication 的作用是：
 * ✅ 告诉 Spring 这是一个启动类
 * ✅ 启动自动配置功能
 * ✅ 自动扫描当前包路径下的所有 Bean
 */
@SpringBootApplication
/*
 * 笔记类型：知识学习
 * 
 * 学习对象：Spring gateway框架
 * 
 * 内容：
 * 简单来说，@EnableConfigurationProperties(UriConfiguration.class) 的作用是；
 * 👉 告诉 Spring：请把 application.yml 或 application.properties 中的配置，自动绑定到
 * UriConfiguration 这个类；
 * 实现的效果就是：Spring会去扫描并加载UriConfiguration类；
 * 如果不加这个注解，Spring根本就不会去扫描UriConfiguration类，UriConfiguration也就不会生效。
 * 
 */
@EnableConfigurationProperties(UriConfiguration.class)
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	/*
	 * 笔记类型：知识学习
	 * 
	 * 学习对象：Spring gateway框架
	 * 
	 * 内容：
	 * Spring Boot 启动时，会扫描所有带 @Bean 注解的方法；
	 * @Bean 标记的方法，Spring 会将其返回的对象加入 Spring 容器（ApplicationContext）；
	 * Gateway 会从 Spring 容器中 自动查找 RouteLocator 类型的 Bean，并自动加载这些路由。
	 */
	@Bean
	/*
	 * 笔记类型：知识学习
	 * 
	 * 学习对象：Spring gateway框架
	 * 
	 * 内容：
	 * 一个 Gateway，写一个 RouteLocator Bean，里面集中写多个 .route() 是最标准、最简单、最推荐的写法。
	 * 写多个带@Bean的Route方法的写法是允许的，Spring Cloud Gateway 会自动把所有 RouteLocator Bean 合并。
	 */
	public RouteLocator myRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
		String httpUri = uriConfiguration.getHttpbin();
		return builder.routes()
				.route(p -> p
						.path("/get")
						.filters(f -> f.addRequestHeader("Hello", "World"))
						.uri(httpUri))
				.route(p -> p
						.host("*.circuitbreaker.com")
						.filters(f -> f
								.circuitBreaker(config -> config
										.setName("mycmd")
										.setFallbackUri("forward:/fallback")))
						.uri(httpUri))
				.build();
	}
}
