package com.dflex.ircs.portal.payer.repository;

import com.dflex.ircs.portal.payer.entity.Payer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayerRepository extends JpaRepository<Payer,Long> {
}
