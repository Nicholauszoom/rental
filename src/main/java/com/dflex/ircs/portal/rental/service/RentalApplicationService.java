package com.dflex.ircs.portal.rental.service;

import com.dflex.ircs.portal.rental.dto.RentalApplicationDto;
import com.dflex.ircs.portal.rental.entity.Building;
import com.dflex.ircs.portal.rental.entity.RentalApplication;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface RentalApplicationService {
    ResponseEntity<RentalApplication> saveRentalApplication(
            RentalApplicationDto rentalApplicationDto, HttpServletRequest request);


    List<RentalApplicationDto> getAllRentalApplication(HttpServletRequest request);

    void deleteRentalApplication(Long rentalApplicationId);

    Optional<RentalApplication> findById(Long rentalApplicationId);
}
