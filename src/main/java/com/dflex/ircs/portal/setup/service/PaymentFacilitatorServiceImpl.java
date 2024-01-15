package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.setup.entity.PaymentFacilitator;
import com.dflex.ircs.portal.setup.repository.PaymentFacilitatorRepository;


/**
 * 
 * @author Augustino Mwageni
 *
 */

@Service
public class PaymentFacilitatorServiceImpl implements PaymentFacilitatorService{
	
	@Autowired
	private PaymentFacilitatorRepository paymentFacilitatorRepository;

	@Override
	public Optional<PaymentFacilitator> findById(Long id) {
		return paymentFacilitatorRepository.findById(id);
	}

	@Override
	public PaymentFacilitator savePaymentFacilitator(PaymentFacilitator paymentFacilitator) {
		return paymentFacilitatorRepository.save(paymentFacilitator);
	}

	@Override
	public PaymentFacilitator findByPaymentFacilitatorCodeAndRecordStatusId(String paymentFacilitatorCode,Long statusId) {
		return paymentFacilitatorRepository.findByPaymentFacilitatorCodeAndRecordStatusId(paymentFacilitatorCode,statusId);
	}

	@Override
	public List<PaymentFacilitator> findAll() {
		return paymentFacilitatorRepository.findAll();
	}

	@Override
	public PaymentFacilitator findByPaymentFacilitatorCode(String paymentFacilitatorCode) {
		return paymentFacilitatorRepository.findByPaymentFacilitatorCode(paymentFacilitatorCode);
	}

}