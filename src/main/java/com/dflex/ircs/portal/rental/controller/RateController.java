package com.dflex.ircs.portal.rental.controller;

import com.dflex.ircs.portal.rental.dto.RateDto;
import com.dflex.ircs.portal.rental.dto.UnitDto;
import com.dflex.ircs.portal.rental.entity.Rate;
import com.dflex.ircs.portal.rental.entity.Unit;
import com.dflex.ircs.portal.rental.service.RateService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/rate/")
public class RateController {
    RateService rateService;

    @GetMapping("/{id}")
    public ResponseEntity<Rate> findById(@PathVariable("id") Long rateId) {
        Optional<Rate> rateOptional = rateService.findById(rateId);
        if (rateOptional.isPresent()) {
            return new ResponseEntity<>(rateOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{unitId}")
    public ResponseEntity<Rate> findByUnitId(@PathVariable("unitId") Long unitId) {
        Optional<Rate> rateOptional = rateService.findByUnitId(unitId);
        if (rateOptional.isPresent()) {
            return new ResponseEntity<>(rateOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Rate> saveRate(@RequestBody RateDto rateDto, HttpServletRequest request) {
        try {
            return rateService.saveRate(rateDto, request);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
