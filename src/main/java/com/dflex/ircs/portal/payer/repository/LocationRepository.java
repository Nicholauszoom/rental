package com.dflex.ircs.portal.payer.repository;

import com.dflex.ircs.portal.payer.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
