package com.dflex.ircs.portal.rental.controller;

import com.dflex.ircs.portal.rental.dto.StatusDto;
import com.dflex.ircs.portal.rental.dto.UnitDto;
import com.dflex.ircs.portal.rental.entity.Status;
import com.dflex.ircs.portal.rental.entity.Unit;
import com.dflex.ircs.portal.rental.service.StatusService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status/")
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllStatus(HttpServletRequest request) {
        try {
            List<StatusDto> status = statusService.getAllStatus(request);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            String errorMessage = "Failed to retrieve the list of status";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Status> saveStatus(
            @RequestBody StatusDto statusDto, HttpServletRequest request) {
        try {
            return statusService.saveStatus(statusDto, request);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
