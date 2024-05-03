package com.dflex.ircs.portal.rental.service;

import com.dflex.ircs.portal.rental.dto.UnitDto;
import com.dflex.ircs.portal.rental.entity.Unit;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UnitService {

    List<Unit> getAllUnits(HttpServletRequest request);

    Optional<Unit> findById(Long unitId);

    Optional<Unit> findByBuildingId(Long buildingId);

    ResponseEntity<Unit> saveUnit(UnitDto unitDto, HttpServletRequest request);

    List<UnitDto> findAllByBuildingId(Long buildingId);

    void deleteUnit(Long unitId);

    void updateUnit(Long unitId, UnitDto unitDto);
}
