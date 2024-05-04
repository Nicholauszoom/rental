package com.dflex.ircs.portal.rental.repository;

import com.dflex.ircs.portal.rental.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UnitRepository
        extends JpaRepository<Unit, Long> {

    Optional<Unit> findByBuildingId(Long buildingId);

    List<Unit> findAllByBuildingId(Long buildingId);

    Unit findByUnitNumber(String unitNumber);


}
