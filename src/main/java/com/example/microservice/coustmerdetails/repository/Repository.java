package com.example.microservice.coustmerdetails.repository;


import com.example.microservice.coustmerdetails.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<Model,Long> {
    @Query("SELECT m FROM Model m WHERE m.coustmerid = :coustmerid")
    Model findBycoustmerid(Long coustmerid);


}