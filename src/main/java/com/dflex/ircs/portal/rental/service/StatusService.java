package com.dflex.ircs.portal.rental.service;

import com.dflex.ircs.portal.rental.dto.StatusDto;
import com.dflex.ircs.portal.rental.entity.Status;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface StatusService {


    List<StatusDto> getAllStatus(HttpServletRequest request);

    Optional<Status> findById(Long statusId);

    ResponseEntity<Status> saveStatus(StatusDto statusDto, HttpServletRequest request);
}
