package com.dflex.ircs.portal.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.dflex.ircs.portal.data.entity.FormData2;



/**
 * @author Augustino Mwageni
 *
 */
@Transactional
public interface FormData2Repository extends JpaRepository<FormData2, Long> {

}
