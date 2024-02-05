package com.dflex.ircs.portal.payer.api.controller;

import com.dflex.ircs.portal.payer.api.dto.InstitutionDTO;
import com.dflex.ircs.portal.payer.entity.*;
import com.dflex.ircs.portal.payer.service.PayerServiceImpl;

import com.dflex.ircs.portal.util.Response;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/payer")
public class PayerController {

    @Autowired
    private PayerServiceImpl service;

    protected Logger logger = LoggerFactory.getLogger(PayerController.class);

    @PostMapping("/register_payer")
    public ResponseEntity<Response<Payer>> createPayer(
            @RequestBody Payer payer) {

        logger.info("Received request to create revenue source: {}", payer);
        Response<Payer> response = new Response<>();

        try {
            Payer createdPayer = service.save(payer);
            if (createdPayer != null){
                response.setCode("200");
                response.setMessage(" Created Successfully");
                response.setData(createdPayer);

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



    @PostMapping("/add_institution")
    public ResponseEntity<Response<InstitutionDTO>> addInstitution(
            @RequestBody Institution institution) {

        logger.info("Received request to create revenue source: {}", institution);
        Response<InstitutionDTO> response = new Response<>();

        try {
            Institution institutionAdded = service.save(institution);
            if (institutionAdded != null) {
                response.setCode("200");
                response.setMessage(" Created Successfully");
                response.setData(institutionAdded);

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

    @PostMapping("/add_paymentProvider")
    public ResponseEntity<Response<PaymentServiceProvider>> addPaymentProvider(
            @RequestBody PaymentServiceProvider paymentServiceProvider) {

        logger.info("Received request to create revenue source: {}", paymentServiceProvider);
        Response<PaymentServiceProvider> response = new Response<>();

        try {
            PaymentServiceProvider providerAdded = service.save(paymentServiceProvider);
            if (providerAdded != null) {
                response.setCode("200");
                response.setMessage(" Created Successfully");
                response.setData(providerAdded);

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



    @GetMapping("/findPayerByID/{id}")
    public ResponseEntity<Response<Payer>> getPayerById(@PathVariable Long id) {

        logger.info("Received request to create revenue source: {}", id);
        Response<Payer> response = new Response<>();

        try {
            Optional<Payer> getPayer = service.findByPayerId(id);
            if (getPayer != null) {
                response.setCode("200");
                response.setMessage(" Created Successfully");
                response.setData(getPayer);

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
