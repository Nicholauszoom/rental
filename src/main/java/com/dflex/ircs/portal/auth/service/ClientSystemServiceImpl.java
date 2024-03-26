package com.dflex.ircs.portal.auth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.auth.entity.ClientSystem;
import com.dflex.ircs.portal.auth.repository.ClientSystemRepository;

/**
 * 
 * @author Augustino Mwageni
 *
 */

@Service
public class ClientSystemServiceImpl implements ClientSystemService {

	@Autowired
	private ClientSystemRepository  clientSystemRepository;

	@Override
	public Optional<ClientSystem> findById(Long id) {
		return clientSystemRepository.findById(id);
	}

	@Override
	public List<ClientSystem> findAll() {
		return clientSystemRepository.findAll();
	}

	@Override
	public ClientSystem createClientSystem(ClientSystem clientSystem) {
		return clientSystemRepository.save(clientSystem);
	}

	@Override
	public ClientSystem updateClientSystem(ClientSystem clientSystem) {
		return clientSystemRepository.save(clientSystem);
	}

	@Override
	public List<ClientSystem> findAllByClientType(Long clientType) {
		return clientSystemRepository.findAllByClientType(clientType);
	}

}
