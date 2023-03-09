package com.example.microservice.coustmerdetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




//@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication

public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


}
//For a @SpringBootApplication to be discovery-aware, we have to include a Spring Discovery Client (for example, spring-cloud-starter-netflix-eureka-client) into our classpath.
//Then we need to annotate a @Configuration with either @EnableDiscoveryClient or @EnableEurekaClient.
// Note that this annotation is optional if we have the spring-cloud-starter-netflix-eureka-client dependency on the classpath.

