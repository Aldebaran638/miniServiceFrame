package com.miniprogram_frame.miniservice2;

import org.springframework.boot.SpringApplication;

public class TestMiniservice2Application {

	public static void main(String[] args) {
		SpringApplication.from(Miniservice2Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
