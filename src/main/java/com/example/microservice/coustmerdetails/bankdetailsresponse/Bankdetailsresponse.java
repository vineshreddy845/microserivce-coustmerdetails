package com.example.microservice.coustmerdetails.bankdetailsresponse;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Bankdetailsresponse {
    // private Long id; // I don't want show id values to client. so that i commented.
    private Long accountnumber;
    private String branchname;
    private Long balance;
}
