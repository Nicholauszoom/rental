package com.dflex.ircs.portal.payer.api.controller;

import com.dflex.ircs.portal.payer.entity.Contact;
import com.dflex.ircs.portal.payer.service.PayerServiceImpl;
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
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private PayerServiceImpl service;

    protected Logger logger = LoggerFactory.getLogger(ContactController.class);
    @PostMapping("/addContact")
    public ResponseEntity<Response<Contact>> addContact(
            @RequestBody Contact contact) {

        logger.info("Received request to create revenue source: {}", contact);
        Response<Contact> response = new Response<>();

        try {
            Contact contactAdded = service.save(contact);
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
}
