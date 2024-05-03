package com.dflex.ircs.portal.rental.service;

import com.dflex.ircs.portal.rental.dto.BuildingDto;
import com.dflex.ircs.portal.rental.entity.Building;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface BuildingService {

    List<BuildingDto> getAllBuildings(HttpServletRequest request);

    Optional<Building> findById(Long buildingId);

    ResponseEntity<Building> saveBuilding(BuildingDto buildingDto, HttpServletRequest request);




}
