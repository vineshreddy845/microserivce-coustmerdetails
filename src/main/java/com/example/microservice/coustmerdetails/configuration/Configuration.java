package com.example.microservice.coustmerdetails.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
@org.springframework.context.annotation.Configuration
public class Configuration {

   @LoadBalanced// we annoateted here right. Restemplate directly will acts as LoadBalancer.
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
// where we getting below error
//Consider defining a bean of type 'org.springframework.web.client.RestTemplate' in your configuration.
// means for RestTemplate class spring can't creating bean or obejct by auto configurating.
// for that we are creating bean or object in configuration class above.