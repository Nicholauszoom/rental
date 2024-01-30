package com.dflex.ircs.portal.revenue.repository;

import com.dflex.ircs.portal.revenue.entity.SubRevenueResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubRevenueResourceRepository extends JpaRepository<SubRevenueResource, Long> {
}
