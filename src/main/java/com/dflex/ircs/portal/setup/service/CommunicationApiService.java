package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.setup.dto.CommunicationApiDetailsDto;
import com.dflex.ircs.portal.setup.entity.CommunicationApi;


/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface CommunicationApiService {

	public Optional<CommunicationApi> findById(Long id);

	public List<CommunicationApi> findAll();
	
	public CommunicationApi saveCommunicationApi(CommunicationApi communicationApi);
	
	public CommunicationApiDetailsDto findByClientIdClientKeyAndApiCategoryIdAndApiVersionNumber(String clientCode, String clientKey,
			Long apiCategoryId, Long apiVersionNumber);

}
