package com.dflex.ircs.portal.payer.repository;

import com.dflex.ircs.portal.payer.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository  extends JpaRepository<Contact,Long> {
}
