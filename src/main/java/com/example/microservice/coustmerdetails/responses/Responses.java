package com.example.microservice.coustmerdetails.responses;

import com.example.microservice.coustmerdetails.bankdetailsresponse.Bankdetailsresponse;
import com.example.microservice.coustmerdetails.model.Model;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Responses {

    private String name;
    private Model coustmerdetails;
    private Bankdetailsresponse Bankdetails;

}
