package com.dflex.ircs.portal.setup.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.dflex.ircs.portal.setup.entity.FinancialYear;



/**
 * @author Augustino Mwageni
 *
 */
@Transactional
public interface FinancialYearRepository extends JpaRepository<FinancialYear, Long> {

	public Optional<FinancialYear> findByFinancialYearUid(UUID financialYearUid);

	@Query("from FinancialYear e where e.id =:financialYearId and e.recordStatusId =:recordStatusId ")
	public FinancialYear findByFinancialYearIdAndRecordStatusId(Long financialYearId, Long recordStatusId);


}
