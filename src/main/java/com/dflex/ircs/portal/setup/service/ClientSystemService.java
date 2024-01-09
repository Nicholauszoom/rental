package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.setup.entity.ClientSystem;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface ClientSystemService {

	public Optional<ClientSystem> findById(Long id);
	
	public List<ClientSystem> findAll();
	
	public ClientSystem createClientSystem(ClientSystem clientSystem);
	
	public ClientSystem updateClientSystem(ClientSystem clientSystem);

	public List<ClientSystem> findAllByClientType(Long clientType);

}
