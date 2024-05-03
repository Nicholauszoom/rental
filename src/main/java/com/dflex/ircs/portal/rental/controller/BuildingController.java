package com.dflex.ircs.portal.rental.controller;

import com.dflex.ircs.portal.rental.dto.BuildingDto;
import com.dflex.ircs.portal.rental.entity.Building;
import com.dflex.ircs.portal.rental.service.BuildingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/building/")
public class BuildingController {

     BuildingService buildingService;

    @GetMapping("/")
    public ResponseEntity<?> getAllBuildings(HttpServletRequest request) {
        try {
            List<BuildingDto> buildings = buildingService.getAllBuildings(request);
            System.out.println("ok for building........");
            return ResponseEntity.ok(buildings);

        } catch (Exception e) {
            String errorMessage = "Failed to retrieve the list of buildings";
            System.out.println("fail for building........");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Building> findById(@PathVariable("id") Long buildingId) {
        Optional<Building> buildingOptional = buildingService.findById(buildingId);
        if (buildingOptional.isPresent()) {
            return new ResponseEntity<>(buildingOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Building> saveBuilding(@RequestBody BuildingDto buildingDto, HttpServletRequest request) {
    try {
        return buildingService.saveBuilding(buildingDto, request);
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBuilding(@PathVariable("id") Long buildingId) {
        buildingService.deleteBuilding(buildingId);
        return ResponseEntity.ok("Building deleted successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBuilding(@PathVariable("id") Long buildingId, @RequestBody BuildingDto buildingDto) {
        buildingService.updateBuilding(buildingId, buildingDto);
        return ResponseEntity.ok("Building updated successfully");
    }



}
