package com.dflex.ircs.portal.rental.service;

import com.dflex.ircs.portal.rental.dto.StatusDto;
import com.dflex.ircs.portal.rental.entity.Status;
import com.dflex.ircs.portal.rental.repository.StatusRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class StatusServiceImpl implements StatusService{

    StatusRepository statusRepository;

    @Override
    public List<StatusDto> getAllStatus(HttpServletRequest request) {
        List<Status> statuses = statusRepository.findAll();
        List<StatusDto> statusDTOs = new ArrayList<>();
        for (Status status : statuses) {
            StatusDto statusDTO = new StatusDto();
            statusDTO.setId(status.getId());
            statusDTO.setStatusName(status.getStatusName());
            statusDTOs.add(statusDTO);
        }

        return statusDTOs;
    }

    @Override
    public Optional<Status> findById(Long statusId) {
        return statusRepository.findById(statusId);
    }

    @Override
    public ResponseEntity<Status> saveStatus(StatusDto statusDto, HttpServletRequest request) {
        try {
            Status status = new Status();
            status.getId();
            String.valueOf(status.getStatusUid());
            status.setStatusName(statusDto.getStatusName());

            Status savedStatus = statusRepository.save(status);

            return ResponseEntity.ok(savedStatus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
