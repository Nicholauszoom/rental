package com.dflex.ircs.portal.payer.repository;

import com.dflex.ircs.portal.payer.entity.PaymentServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentServiceProviderRepository extends JpaRepository<PaymentServiceProvider, Long> {
}
