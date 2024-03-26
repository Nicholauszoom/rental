/**package com.dflex.ircs.portal.application.api.controller;

import com.dflex.ircs.portal.application.entity.Application;
import com.dflex.ircs.portal.application.entity.ApprovalWorkFlow;
import com.dflex.ircs.portal.application.entity.ApprovalWorkFlowItem;
import com.dflex.ircs.portal.application.service.ApplicationServiceImps;
import com.dflex.ircs.portal.payer.api.controller.PayerController;
import com.dflex.ircs.portal.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/approval")
public class ApprovalWorkFlowController {

    @Autowired
    private ApplicationServiceImps service;

    protected Logger logger = LoggerFactory.getLogger(ApprovalWorkFlowController.class);


    @PostMapping("/workFlowCreate")
    public ResponseEntity<Response<ApprovalWorkFlow>> workFlowCreate(
            @RequestBody ApprovalWorkFlow approval) {

        logger.info("Received request to create approval work: {}", approval);
        Response<ApprovalWorkFlow> response = new Response<>();

        try {
            ApprovalWorkFlow workFlow = service.addApprovalWork(approval);
            if (workFlow != null) {
                response.setCode("200");
                response.setMessage(" Created Successfully");
                response.setData(workFlow);

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


    @PostMapping("/workFlowItemCreate")
    public ResponseEntity<Response<ApprovalWorkFlowItem>> workFlowItemCreate(
            @RequestBody ApprovalWorkFlowItem approvalItem) {

        logger.info("Received request to create approval work: {}", approvalItem);
        Response<ApprovalWorkFlowItem> response = new Response<>();

        try {
            ApprovalWorkFlowItem workFlowItem = service.addWorkFlowItem(approvalItem);
            if (workFlowItem != null) {
                response.setCode("200");
                response.setMessage(" Created Successfully");
                response.setData(workFlowItem);

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

    @GetMapping("/workFlowItemById/{id}")
    public ResponseEntity<Response<ApprovalWorkFlowItem>> workFlowItemById(@PathVariable Long id) {

        logger.info("Received request to create approval work: {}", id);
        Response<ApprovalWorkFlowItem> response = new Response<>();

        try {
            Optional<ApprovalWorkFlowItem> workFlowItem = service.workFlowItemFindByID(id);
            if (workFlowItem != null) {
                response.setCode("200");
                response.setMessage(" Created Successfully");
                response.setData(workFlowItem);

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setMessage("No data found from that id"+id);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error Failed to create", e);

            response.setCode("500");
            response.setMessage("Internal Server Error");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}**/
