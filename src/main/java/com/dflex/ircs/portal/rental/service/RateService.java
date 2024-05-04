package com.dflex.ircs.portal.rental.service;

import com.dflex.ircs.portal.rental.dto.RateDto;
import com.dflex.ircs.portal.rental.entity.Rate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface RateService {
    Optional<Rate> findById(Long rateId);

    Optional<Rate> findByUnitId(Long unitId);

    ResponseEntity<Rate> saveRate(RateDto rateDto, HttpServletRequest request);

    void deleteRate(Long rateId);

    void updateRate(Long rateId, RateDto rateDto);
}
