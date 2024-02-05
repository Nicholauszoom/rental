package com.dflex.ircs.portal.revenue.api.controller;

import com.dflex.ircs.portal.revenue.entity.SubRevenueResource;
import com.dflex.ircs.portal.revenue.service.SubRevenueSourceServices;
import com.dflex.ircs.portal.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subRevenueSource")
public class SubRevenueController {

   @Autowired
   private SubRevenueSourceServices service;
    protected Logger logger = LoggerFactory.getLogger(SubRevenueController.class);
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
}
