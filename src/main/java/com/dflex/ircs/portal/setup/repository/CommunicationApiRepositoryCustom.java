package com.dflex.ircs.portal.setup.repository;

import org.springframework.transaction.annotation.Transactional;

import com.dflex.ircs.portal.setup.dto.CommunicationApiDetailsDto;

/**
 * @author Augustino Mwageni
 *
 */
@Transactional
public interface CommunicationApiRepositoryCustom {

	public CommunicationApiDetailsDto findByClientIdClientKeyAndApiCategoryIdAndApiVersionNumber(String clientCode, String clientKey,
			Long apiCategoryId, Long apiVersionNumber);

}
