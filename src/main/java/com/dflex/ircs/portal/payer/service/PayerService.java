package com.dflex.ircs.portal.payer.service;

import com.dflex.ircs.portal.payer.entity.*;

import java.util.Optional;

public interface PayerService {

    Optional<Contact> findByContactId(Long contactId);
    Contact save(Contact contact);

    Payer save(Payer payer);

    Optional<Payer> findByPayerId(Long payerId);

    Institution save(Institution institution);

    Location save(Location location);

    Optional<Institution> findByInstitutionId(Long institutionId);

    PaymentServiceProvider save(PaymentServiceProvider paymentServiceProvider);

    Optional<PaymentServiceProvider> findByProviderId(Long providerId);

    Optional<Location> findByLocationId(Long locationId);

    Location findAllLocation();
}
