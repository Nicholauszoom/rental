package com.dflex.ircs.portal.module.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dflex.ircs.portal.module.entity.AppFormField;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface AppFormFieldRepository extends JpaRepository<AppFormField,Long> {

	@Query("from AppFormField f where f.appForm.appFormUid =:appFormUid and f.recordStatusId =:recordStatusId "
			+ " order by f.fieldOrder asc")
	public List<AppFormField> findByAppFormUidAndRecordStatusId(UUID appFormUid,Long recordStatusId);

	@Query(value="select f.data_field_location||'.'||f.data_field_name from tab_app_form_field f,tab_app_form a where f.app_form_id = a.id and a.app_form_uid =:appFormUid "
			+ " and f.show_on_list = 't' order by f.field_order asc",nativeQuery=true)
	public List<String> findAppFormDataListFieldsByAppFormUid(UUID appFormUid);

	@Query("from AppFormField f where f.appForm.appFormUid =:appFormUid and f.recordStatusId =:recordStatusId "
			+ " and f.showOnDetail =:showOnDetail order by f.fieldOrder asc")
	public List<AppFormField> findByAppFormUidAndShowOnDetailAndRecordStatusId(UUID appFormUid, Boolean showOnDetail,
			Long recordStatusId);


}