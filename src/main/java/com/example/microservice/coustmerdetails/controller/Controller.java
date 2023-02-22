package com.example.microservice.coustmerdetails.controller;

import com.example.microservice.coustmerdetails.bankdetailsresponse.Bankdetailsresponse;
import com.example.microservice.coustmerdetails.feginclient.FeignClient;
import com.example.microservice.coustmerdetails.model.Model;
import com.example.microservice.coustmerdetails.repository.Repository;
import com.example.microservice.coustmerdetails.responses.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

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
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("Calling coustmer-bankdetails service with coustmerid: {}", coustmerid);
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

}
