package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.setup.dto.RevenueSourceDetailsDto;
import com.dflex.ircs.portal.setup.entity.RevenueSource;
import com.dflex.ircs.portal.setup.repository.RevenueSourceRepository;



@Service
public class RevenueSourceServiceImpl implements RevenueSourceService {
	
	@Autowired
	private RevenueSourceRepository revenueSourceRepository;

	@Override
	public Optional<RevenueSource> findById(Long id) {
		return revenueSourceRepository.findById(id);
	}

	@Override
	public RevenueSource saveRevenueSource(RevenueSource revenueSource) {
		return revenueSourceRepository.save(revenueSource);
	}

	@Override
	public List<RevenueSourceDetailsDto> findDetailsByServiceDepartmentUidAndAppModuleUidAndRecordStatusId(
			UUID departmentUid, UUID moduleUid, Long recordStatusId) {
		return revenueSourceRepository.findDetailsByServiceDepartmentUidAndAppModuleUidAndRecordStatusId(
				departmentUid,moduleUid,recordStatusId);
	}

	@Override
	public List<RevenueSourceDetailsDto> findDetailsByServiceInstitutionUidAndAppModuleUidAndRecordStatusId(
			UUID serviceInstitutionUid, UUID appModuleUid, Long recordStatusId) {
		return revenueSourceRepository.findDetailsByServiceInstitutionUidAndAppModuleUidAndRecordStatusId(
				serviceInstitutionUid,appModuleUid,recordStatusId);
	}

	@Override
	public RevenueSource findByRevenueSourceUid(UUID revenueSourceUid) {
		return revenueSourceRepository.findByRevenueSourceUid(revenueSourceUid);
	}

}