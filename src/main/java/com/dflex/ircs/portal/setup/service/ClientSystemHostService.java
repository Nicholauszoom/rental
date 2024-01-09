package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.setup.entity.ClientSystemHost;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface ClientSystemHostService {

	public Optional<ClientSystemHost> findById(Long id);
	
	public List<ClientSystemHost> findAll();
	
	public ClientSystemHost saveClientSystemHost(ClientSystemHost clientSystemHost);
	
	public Optional<ClientSystemHost> findByClientKeyAndRecordStatusIdAndIpAddress(String clientKey,Long recordStatusId, String ipAddress);

	public Boolean existsByClientKeyAndRecordStatusIdAndIpAddress(String clientKey,Long recordStatusId, String ipAddress);
}
