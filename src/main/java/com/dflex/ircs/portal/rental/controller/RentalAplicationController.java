package com.dflex.ircs.portal.rental.controller;

import com.dflex.ircs.portal.rental.dto.BuildingDto;
import com.dflex.ircs.portal.rental.dto.RentalApplicationDto;
import com.dflex.ircs.portal.rental.dto.UnitDto;
import com.dflex.ircs.portal.rental.entity.Building;
import com.dflex.ircs.portal.rental.entity.RentalApplication;
import com.dflex.ircs.portal.rental.service.ApprovalService;
import com.dflex.ircs.portal.rental.service.RentalApplicationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rental_application/")
public class RentalAplicationController {

    private final RentalApplicationService rentalApplicationService;
    private final ApprovalService approvalService;

    public RentalAplicationController(RentalApplicationService rentalApplicationService,
                                      ApprovalService approvalService) {
        this.rentalApplicationService = rentalApplicationService;
        this.approvalService = approvalService;
    }


    @PostMapping("/create")
    public ResponseEntity<RentalApplication> saveRentalApplication(
                    @RequestBody RentalApplicationDto rentalApplicationDto,
                    HttpServletRequest request) {
        try {
            return rentalApplicationService.saveRentalApplication(rentalApplicationDto, request);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllRentalApplication(
            HttpServletRequest request) {
        try {
            List<RentalApplicationDto> rentalApplicationDtos =
                    rentalApplicationService.getAllRentalApplication(request);
            return ResponseEntity.ok(rentalApplicationDtos);
        } catch (Exception e) {
            String errorMessage = "Failed to retrieve the list of rental application";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalApplication> findById(
            @PathVariable("id") Long rentalApplicationId) {
        Optional<RentalApplication> rentalApplicationOptional =
                rentalApplicationService.findById(rentalApplicationId);
        return rentalApplicationOptional.map(rentalApplication ->
                new ResponseEntity<>(rentalApplication, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteApplication(
            @PathVariable("id") Long rentalApplicationId) {
        rentalApplicationService.deleteRentalApplication(rentalApplicationId);
        return ResponseEntity.ok("Rental Application deleted successfully");
    }

    @PostMapping("/{rentalApplicationId}/approve")
    public  ResponseEntity<String> approveApplication(
            @PathVariable Long rentalApplicationId,
            @RequestParam String approverName) throws Exception{
        approvalService.approveApplication(rentalApplicationId,  approverName);
         return ResponseEntity.ok("application approved successfuly");
    }


}
