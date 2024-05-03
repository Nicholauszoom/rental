package com.dflex.ircs.portal.rental.repository;

import com.dflex.ircs.portal.rental.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    Building findByPropertyNumber(String propertyNumber);

    Building findById(Building building);
}
