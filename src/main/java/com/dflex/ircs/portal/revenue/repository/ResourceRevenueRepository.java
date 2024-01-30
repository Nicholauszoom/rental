package com.dflex.ircs.portal.revenue.repository;

import com.dflex.ircs.portal.revenue.entity.RevenueResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRevenueRepository extends JpaRepository<RevenueResource, Long> {

}
