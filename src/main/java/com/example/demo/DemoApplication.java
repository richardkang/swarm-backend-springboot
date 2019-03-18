package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.ctl.ApiController;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringCloudApplication
@ComponentScan("com.example.demo")
@Slf4j
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	public Docket createQueryRestApi() {
		log.info("Docket............");
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Api")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example.demo.ctl"))
				.paths(PathSelectors.any())
				.build();
	}


}
