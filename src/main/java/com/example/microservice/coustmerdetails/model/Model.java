package com.example.microservice.coustmerdetails.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="coustmerdetails")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="coustmerid")
    private int coustmerid;
    @Column(name="firstname")
    private String firstname;
    @Column(name="lastname")
    private String lastname;
}
