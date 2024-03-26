package com.dflex.ircs.portal.data.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.dflex.ircs.portal.data.entity.FormDataTableField;



/**
 * @author Augustino Mwageni
 *
 */
@Transactional
public interface FormDataTableFieldRepository extends JpaRepository<FormDataTableField, Long> {

	@Query("from FormDataTableField fd where fd.formDataTable.formDataTableUid =:formDataTableUid "
			+ " and fd.recordStatusId =:recordStatusId ")
	public List<FormDataTableField> findByFormDataTableUidAndRecordStatusId(UUID formDataTableUid, Long recordStatusId);

}
