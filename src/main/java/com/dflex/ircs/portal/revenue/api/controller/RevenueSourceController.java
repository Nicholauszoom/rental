package com.dflex.ircs.portal.revenue.api.controller;

import com.dflex.ircs.portal.revenue.api.dto.RevenueSourceDTO;
import com.dflex.ircs.portal.revenue.entity.ResourceRevenue;
import com.dflex.ircs.portal.revenue.service.RevenueSourceImps;
import com.dflex.ircs.portal.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/revenueSource")
public class RevenueSourceController {

    @Autowired
    private RevenueSourceImps service;

    protected Logger logger = LoggerFactory.getLogger(RevenueSourceController.class);

    @PostMapping("/createRevenueSource")
    public ResponseEntity<Response<RevenueSourceDTO>> createRevenueSource(
            @RequestBody ResourceRevenue revenueSource) {

        logger.info("Received request to create revenue source: {}", revenueSource);

        try {

            ResourceRevenue createdSource = service.addSource(revenueSource);

            if (createdSource != null) {
                Response<RevenueSourceDTO> response = new Response<>();
                response.setCode("200");
                response.setMessage("Revenue Source Created Successfully");
                response.setData(createdSource);

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                Response<RevenueSourceDTO> response = new Response<>();
                response.setCode("500");
                response.setMessage("Failed to create revenue source");

                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error creating revenue source", e);
            Response<RevenueSourceDTO> response = new Response<>();
            response.setCode("500");
            response.setMessage("Internal Server Error");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}


