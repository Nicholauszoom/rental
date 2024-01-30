package com.dflex.ircs.portal.revenue.repository;


import com.dflex.ircs.portal.revenue.entity.Estimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimateRepository extends JpaRepository<Estimate, Long> {

}
