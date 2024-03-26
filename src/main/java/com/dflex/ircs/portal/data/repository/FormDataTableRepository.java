package com.dflex.ircs.portal.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.dflex.ircs.portal.data.entity.FormDataTable;



/**
 * @author Augustino Mwageni
 *
 */
@Transactional
public interface FormDataTableRepository extends JpaRepository<FormDataTable, Long> {

	public List<FormDataTable> findByRecordStatusId(Long recordStatusId);

}
