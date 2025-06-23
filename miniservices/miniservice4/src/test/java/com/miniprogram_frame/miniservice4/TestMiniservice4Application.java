package com.miniprogram_frame.miniservice4;

import org.springframework.boot.SpringApplication;

public class TestMiniservice4Application {

	public static void main(String[] args) {
		SpringApplication.from(Miniservice4Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
