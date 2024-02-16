/**package com.dflex.ircs.portal.application.api.controller;

import com.dflex.ircs.portal.application.entity.Application;
import com.dflex.ircs.portal.application.entity.ApplicationDetails;
import com.dflex.ircs.portal.application.service.ApplicationServiceImps;
import com.dflex.ircs.portal.payer.api.controller.PayerController;
import com.dflex.ircs.portal.payer.entity.Contact;
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
@RequestMapping("/api/application")
public class ApplicationController {

    @Autowired
    private ApplicationServiceImps service;

    protected Logger logger = LoggerFactory.getLogger(PayerController.class);

    @PostMapping("/createApplication")
    public ResponseEntity<Response<Application>> createApplication(
            @RequestBody Application application) {

        logger.info("Received request to create revenue source: {}", application);
        Response<Application> response = new Response<>();

        try {
            Application contactAdded = service.createApplication(application);
            if (contactAdded != null) {
                response.setCode("200");
                response.setMessage(" Created Successfully");
                response.setData(contactAdded);

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


    @GetMapping("/getApplicationAll")
    public ResponseEntity<Response<Application>> getApplicationAll() {

        Response<Application> response = new Response<>();
        try {
            Application allApplication = service.getAllApplication();
            if (allApplication != null) {
                response.setCode("200");
                response.setMessage(" Created Successfully");
                response.setData(allApplication);

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

    @GetMapping("/getApplicationById/{id}")
    public ResponseEntity<Response<Application>> getApplicationById(@PathVariable Long id) {

        logger.info("Received request to get application by id: {}", id);

        Response<Application> response = new Response<>();
        try {
            Optional<Application> getApplication = service.findApplicationById(id);
            if (getApplication != null) {
                response.setCode("200");
                response.setMessage(" Created Successfully");
                response.setData(getApplication);

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {

                response.setCode("500");
                response.setMessage("Failed to get the is of the applications");

                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error Failed to create", e);

            response.setCode("500");
            response.setMessage("Internal Server Error");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/createAppDetails")
    public ResponseEntity<Response<ApplicationDetails>> createAppDetails(
            @RequestBody ApplicationDetails appDetails) {

        logger.info("Received request to create revenue source: {}", appDetails);
        Response<ApplicationDetails> response = new Response<>();

        try {
            ApplicationDetails details = service.addAppDetails(appDetails);
            if (details != null) {
                response.setCode("200");
                response.setMessage(" Created Successfully");
                response.setData(details);

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

    @GetMapping("/getAppDetailById/{id}")
    public ResponseEntity<Response<Application>> getAppDetailsById(@PathVariable Long id) {

        logger.info("Received request to get application by id: {}", id);

        Response<Application> response = new Response<>();
        try {
            Optional<ApplicationDetails> getAppDetails = service.findAppDetailById(id);
            if (getAppDetails != null) {
                response.setCode("200");
                response.setMessage(" Created Successfully");
                response.setData(getAppDetails);

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setMessage("Failed to get the is of the applications");
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
**/