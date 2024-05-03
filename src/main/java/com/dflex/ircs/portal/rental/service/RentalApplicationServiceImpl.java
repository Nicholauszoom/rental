package com.dflex.ircs.portal.rental.service;

import com.dflex.ircs.portal.payer.repository.PayerRepository;

import com.dflex.ircs.portal.rental.dto.RentalApplicationDto;

import com.dflex.ircs.portal.rental.entity.Building;
import com.dflex.ircs.portal.rental.entity.Rate;
import com.dflex.ircs.portal.rental.entity.RentalApplication;
import com.dflex.ircs.portal.rental.entity.Unit;
import com.dflex.ircs.portal.rental.repository.BuildingRepository;
import com.dflex.ircs.portal.rental.repository.RateRepository;
import com.dflex.ircs.portal.rental.repository.RentalApplicationRepository;
import com.dflex.ircs.portal.rental.repository.UnitRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RentalApplicationServiceImpl implements RentalApplicationService{

    BuildingRepository buildingRepository;
    UnitRepository unitRepository;
    PayerRepository payerRepository;

    RateRepository rateRepository;

    RentalApplicationRepository rentalApplicationRepository;

    @Override
    public ResponseEntity<RentalApplication> saveRentalApplication(RentalApplicationDto rentalApplicationDto, HttpServletRequest request) {
        try {
            RentalApplication rentalApplication = new RentalApplication();
            rentalApplication.getId();
            String.valueOf(rentalApplication.getRentalApplicationUid());

            rentalApplication.setBuilding(buildingRepository.findByPropertyNumber(rentalApplicationDto.getPropertyNumber()));
            rentalApplication.setUnit(unitRepository.findByUnitNumber(rentalApplicationDto.getUnitNumber()));
            rentalApplication.setPayer(payerRepository.getReferenceById(rentalApplication.getId()));

            RentalApplication savedApplication = rentalApplicationRepository.save(rentalApplication);

            return ResponseEntity.ok(savedApplication);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public List<RentalApplicationDto> getAllRentalApplication(HttpServletRequest request) {
        List<RentalApplication> rentalApplications = rentalApplicationRepository.findAll();

        List<RentalApplicationDto> rentalApplicationDtos = new ArrayList<>();
        for (RentalApplication rentalApplication : rentalApplications) {
            Building buildingDetails = buildingRepository.findById(rentalApplication.getBuilding());
            Unit unitDetail = unitRepository.findByUnitNumber(rentalApplication.getUnit().getUnitNumber());
            Optional<Rate> rateDetail = rateRepository.findByUnitId(unitDetail.getId());

            RentalApplicationDto rentalApplicationDto = new RentalApplicationDto();
            rentalApplicationDto.setId(rentalApplication.getId());
            rentalApplicationDto.setBlockNumber(buildingDetails.getBlockNumber());
            rentalApplicationDto.setLocation(buildingDetails.getLocation());
            rentalApplicationDto.setPropertyArea(buildingDetails.getPropertyArea());
            rentalApplicationDto.setPlotNumber(buildingDetails.getPlotNumber());
            rentalApplicationDto.setUnitName(unitDetail.getUnitName());
            rentalApplicationDto.setUnitSize(unitDetail.getUnitSize());
            rentalApplicationDto.setFixedPrice(rateDetail.get().getFixedPrice());
            rentalApplicationDto.setPriceType(rateDetail.get().getPriceType());
            rentalApplicationDto.setDynamicPrice(rateDetail.get().getDynamicPrice());

            rentalApplicationDtos.add(rentalApplicationDto);
        }

        return rentalApplicationDtos;
    }
}
