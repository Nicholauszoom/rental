package com.dflex.ircs.portal.payer.service;

import com.dflex.ircs.portal.payer.entity.*;
import com.dflex.ircs.portal.payer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PayerServiceImpl implements PayerService{


    @Autowired
    private PayerRepository payerRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private PaymentServiceProviderRepository  paymentRepository;


    @Override
    public Contact save(Contact contact) {return contactRepository.save(contact);
    }

    @Override
    public Location save(Location location) {return locationRepository.save(location);
    }
    @Override
    public Optional<Contact> findByContactId(Long contactId) {
        return contactRepository.findById(contactId);
    }
     @Override
    public Payer save(Payer payer) {
         return payerRepository.save(payer);
     }
     @Override
    public Optional<Payer> findByPayerId(Long payerId) {
         return payerRepository.findById(payerId);
     }


     @Override
    public Institution save(Institution institution) {
         return institutionRepository.save(institution);
     }

     @Override
     public Optional<Institution> findByInstitutionId(Long institutionId) {
         return institutionRepository.findById(institutionId);
     }

     @Override
    public PaymentServiceProvider save(PaymentServiceProvider paymentServiceProvider) {
         return paymentRepository.save(paymentServiceProvider);
     }

     @Override
    public Optional<PaymentServiceProvider> findByProviderId(Long providerId) {
         return paymentRepository.findById(providerId);
     }

     @Override
    public Optional<Location> findByLocationId(Long locationId) {
         return locationRepository.findById(locationId);
     }

}
