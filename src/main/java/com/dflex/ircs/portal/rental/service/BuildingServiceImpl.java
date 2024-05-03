package com.dflex.ircs.portal.rental.service;

import com.dflex.ircs.portal.rental.dto.BuildingDto;
import com.dflex.ircs.portal.rental.entity.Building;
import com.dflex.ircs.portal.rental.repository.BuildingRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class BuildingServiceImpl implements BuildingService{

    BuildingRepository buildingRepository;

    @Override
    public List<BuildingDto> getAllBuildings(HttpServletRequest request) {
        List<Building> buildings = buildingRepository.findAll();

        List<BuildingDto> buildingDTOs = new ArrayList<>();
        for (Building building : buildings) {
            BuildingDto buildingDTO = new BuildingDto();
            buildingDTO.setId(building.getId());
            buildingDTO.setBlockNumber(building.getBlockNumber());
            buildingDTO.setLocation(building.getLocation());
            buildingDTO.setPropertyArea(building.getPropertyArea());
            buildingDTO.setPlotNumber(building.getPlotNumber());
            buildingDTO.setPropertyDistrict(building.getPropertyDistrict());
            buildingDTO.setPropertyRegion(building.getPropertyRegion());
            buildingDTO.setPropertySize(building.getPropertySize());
            buildingDTO.setPropertyName(building.getPropertyName());

            buildingDTOs.add(buildingDTO);
        }
        return buildingDTOs;
    }

    @Override
    public Optional<Building> findById(Long buildingId) {
        return buildingRepository.findById(buildingId);
    }

    @Override
    public ResponseEntity<Building> saveBuilding(BuildingDto buildingDto, HttpServletRequest request) {
        try {
            Building building = new Building();
            building.getId();
            String.valueOf(building.getBuildingUid());
            building.setBlockNumber(buildingDto.getBlockNumber());
            building.setPlotNumber(buildingDto.getPlotNumber());
            building.setLocation(buildingDto.getLocation());
            building.setPropertySize(buildingDto.getPropertySize());
            building.setPropertyRegion(buildingDto.getPropertyRegion());
            building.setPropertyArea(buildingDto.getPropertyArea());
            building.setPropertyDistrict(buildingDto.getPropertyDistrict());
            building.setPropertyNumber(buildingDto.getPropertyNumber());
            building.setPropertyName(buildingDto.getPropertyName());

            Building savedBuilding = buildingRepository.save(building);

            return ResponseEntity.ok(savedBuilding);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public void deleteBuilding(Long buildingId) {
        buildingRepository.deleteById(buildingId);
    }

    @Override
    public void updateBuilding(Long buildingId, BuildingDto buildingDto) {
        Building building = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new RuntimeException());

        building.setPropertyName(buildingDto.getPropertyName());
        building.setBlockNumber(buildingDto.getBlockNumber());
        building.setPropertyNumber(buildingDto.getPropertyNumber());
        building.setPlotNumber(buildingDto.getPlotNumber());
        building.setLocation(buildingDto.getLocation());
        building.setPropertyRegion(buildingDto.getPropertyRegion());
        building.setPropertyDistrict(buildingDto.getPropertyRegion());
        building.setPropertyArea(buildingDto.getPropertyArea());
        building.setPropertySize(buildingDto.getPropertySize());

        buildingRepository.save(building);
    }


}
