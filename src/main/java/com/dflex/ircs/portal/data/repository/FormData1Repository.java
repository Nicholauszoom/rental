package com.dflex.ircs.portal.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.dflex.ircs.portal.data.entity.FormData1;



/**
 * @author Augustino Mwageni
 *
 */
@Transactional
public interface FormData1Repository extends JpaRepository<FormData1, Long> {

	public List<FormData1> findByAppFormUidAndRecordStatusId(UUID appFormformUid, Long recordStatusId);

	@Query("from FormData1 f where f.applicationUid =:applicationUid")
	public Optional<FormData1> findFormData1ByApplicationUid(UUID applicationUid);

}
