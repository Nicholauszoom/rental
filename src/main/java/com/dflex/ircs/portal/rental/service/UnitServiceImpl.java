package com.dflex.ircs.portal.rental.service;


import com.dflex.ircs.portal.application.repository.ApplicantRepository;
import com.dflex.ircs.portal.payer.repository.PayerRepository;
import com.dflex.ircs.portal.rental.dto.UnitDto;
import com.dflex.ircs.portal.rental.entity.Building;
import com.dflex.ircs.portal.rental.entity.Unit;
import com.dflex.ircs.portal.rental.repository.BuildingRepository;
import com.dflex.ircs.portal.rental.repository.StatusRepository;
import com.dflex.ircs.portal.rental.repository.UnitRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UnitServiceImpl implements  UnitService{

    private final UnitRepository unitRepository;

    private final BuildingRepository buildingRepository;

    private final StatusService statusService;

    private final StatusRepository statusRepository;

    private final PayerRepository payerRepository;

    private final ApplicantRepository applicantRepository;

    public UnitServiceImpl(UnitRepository unitRepository,
                           BuildingRepository buildingRepository,
                           StatusService statusService,
                           StatusRepository statusRepository,
                           PayerRepository payerRepository,
                           ApplicantRepository applicantRepository) {
        this.unitRepository = unitRepository;
        this.buildingRepository = buildingRepository;
        this.statusService = statusService;
        this.statusRepository = statusRepository;
        this.payerRepository = payerRepository;
        this.applicantRepository = applicantRepository;
    }


    @Override
    public List<Unit> getAllUnits(HttpServletRequest request) {

        List<Unit> units = unitRepository.findAll();
        return units;
    }

    @Override
    public Optional<Unit> findById(Long unitId) {

        return unitRepository.findById(unitId);
    }

    @Override
    public Optional<Unit> findByBuildingId(Long buildingId) {

        return unitRepository.findByBuildingId(buildingId);
    }



    @Override
    public ResponseEntity<Unit> saveUnit(UnitDto unitDto, HttpServletRequest request) {
        try {
            Unit unit = new Unit();
            unit.getId();
            String.valueOf(unit.getUnitUid());
            unit.setTypeSize(unitDto.getTypeSize());
            unit.setUnitName(unitDto.getUnitName());
            unit.setUnitNumber(unitDto.getUnitNumber());
            unit.setUnitSize(unitDto.getUnitSize());
            unit.setBuilding(buildingRepository.findByPropertyNumber(unitDto.getPropertyNumber()));
            unit.setStatus(statusRepository.findById(unitDto.getStatusId()).orElseThrow(() -> new RuntimeException("cannot find status with id:" + statusRepository.findById(unitDto.getStatusId()))));

            Unit savedUnit = unitRepository.save(unit);

            return ResponseEntity.ok(savedUnit);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @Override
    public List<UnitDto> findAllByBuildingId(Long buildingId) {
        List<Unit> units = unitRepository.findAllByBuildingId(buildingId);

        List<UnitDto> unitDTOs = new ArrayList<>();
        for (Unit unit : units) {
            UnitDto unitDTO = new UnitDto();
            unitDTO.setId(unit.getId());
            unitDTO.setUnitNumber(unit.getUnitNumber());
            unitDTO.setUnitName(unit.getUnitName());
            unitDTO.setUnitSize(unit.getUnitSize());
            unitDTO.setStatusId(unit.getStatus().getId());
            Optional<Building> building =buildingRepository.findById(buildingId);
            if (building.isPresent()) {
                unitDTO.setPropertyNumber(building.get().getPropertyNumber());
            }else {
                throw new RuntimeException("property number not exist");
            }

            unitDTOs.add(unitDTO);
        }

        return unitDTOs;
    }

    @Override
    public void deleteUnit(Long unitId) {
        buildingRepository.deleteById(unitId);
    }

    @Override
    public void updateUnit(Long unitId, UnitDto unitDto) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException());

        unit.setUnitNumber(unitDto.getUnitNumber());
        unit.setUnitSize(unitDto.getUnitSize());
        unit.setUnitName(unitDto.getUnitName());
        unit.setTypeSize(unitDto.getTypeSize());
        unit.setStatus(statusRepository.findById(unitDto.getStatusId()).orElseThrow());
        unit.setBuilding(buildingRepository.findById(unitDto.getBuildingId()).orElseThrow());
        unit.setApplicant(applicantRepository.findById(unitDto.getPayer()).orElseThrow());

        unitRepository.save(unit);
    }

}
