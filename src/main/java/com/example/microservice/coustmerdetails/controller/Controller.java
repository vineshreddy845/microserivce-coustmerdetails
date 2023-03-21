package com.example.microservice.coustmerdetails.controller;

import com.example.microservice.coustmerdetails.bankdetailsresponse.Bankdetailsresponse;
import com.example.microservice.coustmerdetails.feginclient.FeignClient;
import com.example.microservice.coustmerdetails.model.Model;
import com.example.microservice.coustmerdetails.repository.Repository;
import com.example.microservice.coustmerdetails.responses.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/details")
public class Controller {

    @Autowired
    private Repository repo;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Responses response;
    @Autowired
    private FeignClient feignClient;
    @Autowired
    private EurekaDiscoveryClient eurekaDiscoveryClient;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private ServiceInstance serviceInstance;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    //http://localhost:8080/details/save
    @PostMapping("/save")
    public Model save(@RequestBody Model details)
    {
        return repo.save(details);
    }
    //http://localhost:8081/bankdetails/findbyid/1
    @GetMapping("/findby")
    public Optional<Model> get(@RequestBody Model details)
    {
        return repo.findById((long) details.getCoustmerid());
    }

 // communication between two microserivces using RestTemplate.
 // calling another microservice using another microservice.
    @GetMapping("/checking/{coustmerid}")
    public Responses response(@PathVariable(value="coustmerid") Long coustmerid){
        //Logger logger = LoggerFactory.getLogger(this.getClass());
       // logger.info("Calling coustmer-bankdetails service with coustmerid: {}", coustmerid);
        response.setCoustmerdetails(repo.findBycoustmerid(coustmerid));
        response.setName("Any problem contact vinesh");
        response.setBankdetails(restTemplate.getForObject("http://localhost:8081/bankdetails/checking/{coustmerid}", Bankdetailsresponse.class,coustmerid));
       return response;
    }
    // below we used feignclient http to call another microservice.
    // communication between two microserivces using feignclient.
    @GetMapping("/checkings/{coustmerid}")
    public Responses responses(@PathVariable(value="coustmerid") Long coustmerid){
       Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("Calling coustmer-bankdetails service with coustmerid: {}", coustmerid);
        response.setCoustmerdetails(repo.findBycoustmerid(coustmerid));
        response.setName("Any problem contact vinesh");
      //  response.setBankdetails(restTemplate.getForObject("http://localhost:8081/bankdetails/checking/{coustmerid}", Bankdetailsresponse.class,coustmerid));
        response.setBankdetails(feignClient.findBycoustmerid(coustmerid));
        return response;
    }

    @GetMapping("/call")
    public String name(){
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("it is working");
        return "vinesh";
    }

    @GetMapping("/checking")
    public String checkingeurekaclient(){
        Logger logger = LoggerFactory.getLogger(this.getClass());

       return  eurekaDiscoveryClient.description();
    }

    //now call coustmer-bankdetails microserivce by using discovery service.
    //above we hardcoded-right like we used in resttemplate. now we will not hardcoded.discovery-client will pick from discovery server.
    // we added above discovery client object from spring cloud. DicoveryClient discoveryclient
    @GetMapping("/checks/{coustmerid}/{name}")
    public Responses response2(@PathVariable(value="coustmerid") Long coustmerid,@PathVariable(value="name")String name){
        //Logger logger = LoggerFactory.getLogger(this.getClass());
        // logger.info("Calling coustmer-bankdetails service with coustmerid: {}", coustmerid);
        response.setCoustmerdetails(repo.findBycoustmerid(coustmerid));
        response.setName("Any problem contact vinesh");
       List<ServiceInstance> save= discoveryClient.getInstances(name);
        System.out.println(name);
        int index= new Random().nextInt(save.size());// we have two instances 0,1. where random().function will select instance and place in resttemplate.
        System.out.println(index);
        System.out.println(save.get(index).getUri());
        //URI uri = instances.get(index).getUri(); // we can also write uri like these.
        // response.setBankdetails(restTemplate.getForObject(uri + "/bankdetails/checking/{coustmerid}", Bankdetailsresponse.class, coustmerid));
        response.setBankdetails(restTemplate.getForObject(save.get(index).getUri() + "/bankdetails/checking/{coustmerid}", Bankdetailsresponse.class, coustmerid));
            // right here it is happening that save.get(0)->there can be number of instance// give instance. geturi()// will give uri see below rest api ("/uri);
        // above called like these "http://user.attlocal.net:8080"/bankdetails/checking/{coustmerid};
        return response;
    }
    @GetMapping("/uri")
    public URI uri(){
        return serviceInstance.getUri();
        // response is "http://user.attlocal.net:8080"
    }

    //now above we used random() method which it select instance randomly.
    //now we introduce load balancer.which it send request using load-balancer.
    //spring-cloud gives us load-balancer it follows robbin alogorithm.there others to but we use round-robbin.
    // round-robbin send to request to sequentally.we have 8081,8082 .first it sends to 8081 and second request 8082. in same way.
    @GetMapping("/checkss/{coustmerid}/{name}")
    public Responses response4(@PathVariable(value="coustmerid") Long coustmerid,@PathVariable(value="name")String name){
        //Logger logger = LoggerFactory.getLogger(this.getClass());
        // logger.info("Calling coustmer-bankdetails service with coustmerid: {}", coustmerid);
        response.setCoustmerdetails(repo.findBycoustmerid(coustmerid));
        response.setName("Any problem contact vinesh");
       ServiceInstance choose =loadBalancerClient.choose(name);// here actually load balancing is working
       URI uri=choose.getUri();// we are getting url for where load-balancer send request to instance.
       System.out.println(uri);
        response.setBankdetails(restTemplate.getForObject(uri + "/bankdetails/checking/{coustmerid}", Bankdetailsresponse.class, coustmerid));

        return response;
    }
    // there is another way can also do load-balancing . where we should just place @LoadBalanced annotation at RestTemaplte configuration.
    // go to configuration class and see RestTemplate class. where we annotated with @LoadBalanced.
    @GetMapping("/checksss/{coustmerid}/{name}")
    public Responses response5(@PathVariable(value="coustmerid") Long coustmerid,@PathVariable(value="name")String name){
        //Logger logger = LoggerFactory.getLogger(this.getClass());
        // logger.info("Calling coustmer-bankdetails service with coustmerid: {}", coustmerid);
        response.setCoustmerdetails(repo.findBycoustmerid(coustmerid));
        response.setName("Any problem contact vinesh");
       // right here resTemplate act as loadbalancer. because we added @LoadBalancer annotation to restTemplate in configuration file .
        response.setBankdetails(restTemplate.getForObject( "http://COUSTMERBANKDETAILS/bankdetails/checking/{coustmerid}", Bankdetailsresponse.class, coustmerid));
        return response;
    }
}

