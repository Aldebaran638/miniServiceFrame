package com.miniprogram_frame.miniservice1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
/*
 * 笔记类型：知识学习
 * 
 * 学习对象：Spring 框架
 * 
 * 内容：
 * 如果你用 @RestController，Spring 默认认为你要返回的是 字符串、JSON、XML 等响应数据，不会跳转页面。
 */
@RestController
public class Miniservice1Application {

	public static void main(String[] args) {
		SpringApplication.run(Miniservice1Application.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}
}
