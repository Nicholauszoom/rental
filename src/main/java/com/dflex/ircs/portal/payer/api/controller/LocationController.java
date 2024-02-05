package com.dflex.ircs.portal.payer.api.controller;

import com.dflex.ircs.portal.payer.entity.Location;
import com.dflex.ircs.portal.payer.entity.Payer;
import com.dflex.ircs.portal.payer.service.PayerServiceImpl;
import com.dflex.ircs.portal.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    private PayerServiceImpl service;

    protected Logger logger = LoggerFactory.getLogger(LocationController.class);
    @PostMapping("/addLocation")
    public ResponseEntity<Response<Location>> addLocation(
            @RequestBody Location location) {

        logger.info("Received request to create revenue source: {}", location);
        Response<Location> response = new Response<>();

        try {
            Location locationAdded = service.save(location);
            if (locationAdded != null) {
                response.setCode("200");
                response.setMessage(" Created Successfully");
                response.setData(locationAdded);

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {

                response.setCode("500");
                response.setMessage("Failed to create");

                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error Failed to create", e);

            response.setCode("500");
            response.setMessage("Internal Server Error");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findLocation/{id}")
    public ResponseEntity<Response<Location>> getLocationById(@PathVariable Long id) {

        logger.info("Received request to create revenue source: {}", id);
        Response<Location> response = new Response<>();

        try {
            Optional<Location> getLocation = service.findByLocationId(id);
            if (getLocation != null) {
                response.setCode("200");
                response.setMessage(" Created Successfully");
                response.setData(getLocation);

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {

                response.setCode("500");
                response.setMessage("Failed to create");

                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error Failed to create", e);

            response.setCode("500");
            response.setMessage("Internal Server Error");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getLocationAll")
    public ResponseEntity<Response<Location>> getLocationAll() {

        Response<Location> response = new Response<>();

        try {
              Location getLocation = service.findAllLocation();
            if (getLocation != null) {
                response.setCode("200");
                response.setMessage(" Created Successfully");
                response.setData(getLocation);

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {

                response.setCode("500");
                response.setMessage("Failed to create");

                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error Failed to create", e);

            response.setCode("500");
            response.setMessage("Internal Server Error");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
