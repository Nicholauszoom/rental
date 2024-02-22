package com.dflex.ircs.portal.setup.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.dflex.ircs.portal.setup.entity.FinancialYear;



/**
 * @author Augustino Mwageni
 *
 */
@Transactional
public interface FinancialYearRepository extends JpaRepository<FinancialYear, Long> {

	public Optional<FinancialYear> findByFinancialYearUid(UUID financialYearUid);

}
