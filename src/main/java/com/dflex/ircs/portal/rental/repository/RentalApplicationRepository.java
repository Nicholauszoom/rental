package com.dflex.ircs.portal.rental.repository;

import com.dflex.ircs.portal.rental.entity.RentalApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalApplicationRepository extends JpaRepository<RentalApplication,Long> {
}
