package com.dflex.ircs.portal.rental.repository;

import com.dflex.ircs.portal.rental.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Long> {
    Optional<Rate> findByUnitId(Long unitId);
}
