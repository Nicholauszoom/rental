package com.dflex.ircs.portal.rental.controller;

import com.dflex.ircs.portal.rental.dto.BuildingDto;
import com.dflex.ircs.portal.rental.dto.UnitDto;
import com.dflex.ircs.portal.rental.entity.Building;
import com.dflex.ircs.portal.rental.entity.Unit;
import com.dflex.ircs.portal.rental.service.UnitService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/unit/")
public class UnitController {

    UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUnits(HttpServletRequest request) {
        try {
            List<Unit> units = unitService.getAllUnits(request);
            return ResponseEntity.ok(units);
        } catch (Exception e) {
            String errorMessage = "Failed to retrieve the list of unit";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Unit> findById(@PathVariable("id") Long unitId) {
        Optional<Unit> unitOptional = unitService.findById(unitId);
        if (unitOptional.isPresent()) {
            return new ResponseEntity<>(unitOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{buildingId}")
    public ResponseEntity<Unit> findByBuildingId(@PathVariable("buildingId") Long buildingId) {
        Optional<Unit> unitOptional = unitService.findByBuildingId(buildingId);
        if (unitOptional.isPresent()) {
            return new ResponseEntity<>(unitOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/list/{buildingId}")
    public ResponseEntity<?> findAllByBuildingId(@PathVariable("buildingId") Long buildingId) {
        List<UnitDto> unitList = unitService.findAllByBuildingId(buildingId);
        if (unitList != null) {
            return new ResponseEntity<>(unitList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<Unit> saveUnit(@RequestBody UnitDto unitDto, HttpServletRequest request) {
        try {
            return unitService.saveUnit(unitDto, request);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUnit(@PathVariable("id") Long unitId) {
        unitService.deleteUnit(unitId);
        return ResponseEntity.ok("Unit deleted successfully");
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateUnit(@PathVariable("id") Long unitId, @RequestBody UnitDto unitDto) {
        unitService.updateUnit(unitId, unitDto);
        return ResponseEntity.ok("Unit updated successfully");
    }

}
