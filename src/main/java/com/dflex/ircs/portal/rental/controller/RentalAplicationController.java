package com.dflex.ircs.portal.rental.controller;

import com.dflex.ircs.portal.rental.dto.BuildingDto;
import com.dflex.ircs.portal.rental.dto.RentalApplicationDto;
import com.dflex.ircs.portal.rental.entity.Building;
import com.dflex.ircs.portal.rental.entity.RentalApplication;
import com.dflex.ircs.portal.rental.service.RentalApplicationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rental/application")
public class RentalAplicationController {

    RentalApplicationService rentalApplicationService;
    @PostMapping("/create")
    public ResponseEntity<RentalApplication> saveRentalApplication(@RequestBody RentalApplicationDto rentalApplicationDto, HttpServletRequest request) {
        try {
            return rentalApplicationService.saveRentalApplication(rentalApplicationDto, request);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllRentalApplication(HttpServletRequest request) {
        try {
            List<RentalApplicationDto> rentalApplicationDtos = rentalApplicationService.getAllRentalApplication(request);
            return ResponseEntity.ok(rentalApplicationDtos);
        } catch (Exception e) {
            String errorMessage = "Failed to retrieve the list of rental application";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

}
