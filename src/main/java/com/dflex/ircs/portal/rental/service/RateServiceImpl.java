package com.dflex.ircs.portal.rental.service;

import com.dflex.ircs.portal.rental.dto.RateDto;
import com.dflex.ircs.portal.rental.dto.UnitDto;
import com.dflex.ircs.portal.rental.entity.Rate;
import com.dflex.ircs.portal.rental.entity.Unit;
import com.dflex.ircs.portal.rental.repository.BuildingRepository;
import com.dflex.ircs.portal.rental.repository.RateRepository;
import com.dflex.ircs.portal.rental.repository.UnitRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RateServiceImpl implements RateService{

    RateRepository rateRepository;

    BuildingRepository buildingRepository;

    UnitRepository unitRepository;

    @Override
    public Optional<Rate> findById(Long rateId) {
        return rateRepository.findById(rateId);
    }

    @Override
    public Optional<Rate> findByUnitId(Long unitId) {
        return rateRepository.findByUnitId(unitId);
    }

    @Override
    public ResponseEntity<Rate> saveRate(RateDto rateDto, HttpServletRequest request) {
        try {
            Rate rate = new Rate();
            rate.getId();
            String.valueOf(rate.getRateUid());
            rate.setFixedPrice(rateDto.getFixedPrice());
            rate.setPriceType(rateDto.getPriceType());
            rate.setDynamicPrice(rateDto.getDynamicPrice());
            rate.setBuilding(buildingRepository.findByPropertyNumber(rateDto.getPropertyNumber()));
            rate.setUnit(unitRepository.findByUnitNumber(rateDto.getUnitNumber()));

            Rate savedRate = rateRepository.save(rate);

            return ResponseEntity.ok(savedRate);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
