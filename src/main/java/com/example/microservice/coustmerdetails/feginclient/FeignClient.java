package com.example.microservice.coustmerdetails.feginclient;

import com.example.microservice.coustmerdetails.bankdetailsresponse.Bankdetailsresponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//http://localhost:8081/bankdetails/checking/{coustmerid}  // acutally we call by using below one.
@org.springframework.cloud.openfeign.FeignClient(name="abc", url="http://localhost:8081/bankdetails/")
                                                 // name=can be anything.
public interface FeignClient {
    @GetMapping("/checking/{coustmerid}")
    Bankdetailsresponse findBycoustmerid(@PathVariable(value="coustmerid") Long coustmerid);
}
// feignclient is given by netflix. that is adopted by springcloud.
// we have add feignclient dependency from maven repository.
// above we created a method where we can another microserivce. implemetation for that method. is given by spring. we should not worry about that.
