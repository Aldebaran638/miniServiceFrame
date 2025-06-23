package com.miniprogram_frame.miniservice3;

import org.springframework.boot.SpringApplication;

public class TestMiniservice3Application {

	public static void main(String[] args) {
		SpringApplication.from(Miniservice3Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
