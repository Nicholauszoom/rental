package com.dflex.ircs.portal.revenue.api.controller;

import com.dflex.ircs.portal.revenue.api.dto.RevenueSourceDTO;
import com.dflex.ircs.portal.revenue.entity.Estimate;
import com.dflex.ircs.portal.revenue.entity.RevenueResource;
import com.dflex.ircs.portal.revenue.entity.SubRevenueResource;
import com.dflex.ircs.portal.revenue.service.RevenueSourceImps;
import com.dflex.ircs.portal.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/revenueSource")
public class RevenueSourceController {

    @Autowired
    private RevenueSourceImps service;

    protected Logger logger = LoggerFactory.getLogger(RevenueSourceController.class);

    @PostMapping("/createRevenueSource")
    public ResponseEntity<Response<RevenueResource>> createRevenueSource(
            @RequestBody RevenueResource revenueSource) {

        logger.info("Received request to create revenue source: {}", revenueSource);
        Response<RevenueResource> response = new Response<>();

        try {
            RevenueResource createdSource = service.addSource(revenueSource);
            if (createdSource != null) {
                response.setCode("200");
                response.setMessage("Revenue Source Created Successfully");
                response.setData(createdSource);

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {

                response.setCode("500");
                response.setMessage("Failed to create revenue source");

                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error creating revenue source", e);

            response.setCode("500");
            response.setMessage("Internal Server Error");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping ("/revenueSourceById/{id}")
    public ResponseEntity<Response<RevenueResource>> getRevenueSourceById(@PathVariable Long id) {

        logger.info("Received request to create revenue source: {}", id);
        Response<RevenueResource> response = new Response<>();

        try {
            Optional<RevenueResource> createdSource = service.findById(id);
            if (createdSource != null) {
                response.setCode("200");
                response.setMessage("Revenue Source Created Successfully");
                response.setData(createdSource);

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {

                response.setCode("500");
                response.setMessage("Failed to create revenue source");

                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error creating revenue source", e);

            response.setCode("500");
            response.setMessage("Internal Server Error");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping ("/revenueSourceAll")
    public ResponseEntity<Response<RevenueResource>> getRevenueSource() {

        Response<RevenueResource> response = new Response<>();

        try {
            List<RevenueResource> createdSource = service.findAll();
            if (createdSource != null) {
                response.setCode("200");
                response.setMessage("Revenue Source Created Successfully");
                response.setData(createdSource);

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {

                response.setCode("500");
                response.setMessage("Failed to get revenue source");

                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error creating revenue source", e);

            response.setCode("500");
            response.setMessage("Internal Server Error");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //sub revenue source.
    @PostMapping("/createSubRevenueSource")
    public ResponseEntity<Response<SubRevenueResource>> createSubRevenueSource(
            @RequestBody SubRevenueResource subRevenue) {

        logger.info("Received request to create sub revenue source: {}", subRevenue);
        Response<SubRevenueResource> response = new Response<>();

        try {

            SubRevenueResource createdSub = service.addSubRevenueSource(subRevenue);

            if (createdSub != null) {

                response.setCode("200");
                response.setMessage("Revenue Source Created Successfully");
                response.setData(createdSub);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {

                response.setCode("500");
                response.setMessage("Failed to create revenue source");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error creating revenue source", e);
            response.setCode("500");
            response.setMessage("Internal Server Error");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createEstimate")
    public ResponseEntity<Response<Estimate>> createEstimate(
            @RequestBody Estimate estimate) {
        logger.info("Received request to create sub revenue source: {}", estimate);
        Response<Estimate> response = new Response<>();

        try {
            Estimate createdestimate = service.addEstimate(estimate);
            if (createdestimate != null) {

                response.setCode("200");
                response.setMessage("Estimated  Created Successfully");
                response.setData(createdestimate);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {

                response.setCode("500");
                response.setMessage("Failed to create estimated");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Failed to create estimated", e);
            response.setCode("500");
            response.setMessage("Internal Server Error");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}


