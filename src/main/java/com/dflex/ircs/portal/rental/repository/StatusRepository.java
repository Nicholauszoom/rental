package com.dflex.ircs.portal.rental.repository;

import com.dflex.ircs.portal.rental.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository
        extends JpaRepository<Status, Long> {
}
