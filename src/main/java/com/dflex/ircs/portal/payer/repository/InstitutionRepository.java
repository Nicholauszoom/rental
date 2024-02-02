package com.dflex.ircs.portal.payer.repository;

import com.dflex.ircs.portal.payer.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
}
