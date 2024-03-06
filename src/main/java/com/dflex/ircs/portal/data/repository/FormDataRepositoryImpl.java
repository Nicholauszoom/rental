package com.dflex.ircs.portal.data.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dflex.ircs.portal.module.entity.AppFormField;
import com.dflex.ircs.portal.util.Constants;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Augustino Mwageni
 *
 */
@Repository
@Transactional
public class FormDataRepositoryImpl implements FormDataRepositoryCustom  {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<LinkedHashMap<String,Object>> findAppFormDataListByAppFormUidAndDataPathAndDataFields(UUID appFormformUid,
			String dataPath, List<String> dataListFields) {
		
		List<LinkedHashMap<String,Object>> listData= new ArrayList<>();
		String tableName = "tab_form_data1";
		if(dataPath.equals(Constants.DATA_PATH_2)) {
			tableName = "tab_form_data2";
		} 
		
		String sqlQuery = "select " +StringUtils.join(dataListFields,',')
				+ " from  "+tableName
				+ " where app_form_uid =:appFormUid "
				+ " and record_status_id =:recordStatusId "
				+ " order by id ";

		Query q = entityManager.createNativeQuery(sqlQuery);
		q.setParameter("appFormUid", appFormformUid);
		q.setParameter("recordStatusId",Constants.RECORD_STATUS_ACTIVE);
		
		try {
			
			List<Object[]> results = q.getResultList();
			if(results != null && !results.isEmpty()) {
				
				for(Object[] res : results) {
					
					LinkedHashMap<String,Object> entry = new LinkedHashMap<>();
					
					for(int i=0;i < dataListFields.size();i++) {
						entry.put(dataListFields.get(i), res[i]==null?"":res[i].toString());
					}
					
					listData.add(entry);
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return listData;
	}

	@Override
	public LinkedHashMap<String, Object> findAppFormDataDetailByAppFormUidAndApplicationUidAndDataPathAndDataFields(
			UUID appFormformUid, UUID applicationUid, String dataPath, List<AppFormField> dataDetailFields) {
		
		LinkedHashMap<String,Object> data= new LinkedHashMap<>();
		
		String tableName = "tab_form_data1";
		if(dataPath.equals(Constants.DATA_PATH_2)) {
			tableName = "tab_form_data2";
		} 
		
		List<String> dataFields = dataDetailFields.stream().map(field -> field.getDataFieldName()).collect(Collectors.toList());
		dataFields.addAll(Constants.DATA_DETAIL_DEFAULT_FIELDS);
		
		String sqlQuery = "select " +StringUtils.join(dataFields,',')
				+ " from  "+tableName
				+ " where app_form_uid =:appFormUid "
				+ " and uid_field1 =:applicationUid "
				+ " and record_status_id =:recordStatusId "
				+ " order by id ";

		Query q = entityManager.createNativeQuery(sqlQuery);
		q.setParameter("appFormUid", appFormformUid);
		q.setParameter("applicationUid", applicationUid);
		q.setParameter("recordStatusId",Constants.RECORD_STATUS_ACTIVE);
		
		try {
			
			List<Object[]> results = q.getResultList();
			if(results != null && !results.isEmpty()) {
				
				for(Object[] res : results) {
					
					int count = 0;
					for(int i=0;i < dataDetailFields.size();i++) {
						data.put(dataDetailFields.get(i).getFormDisplayText(), res[i]==null?"":res[i].toString());
						count ++;
					}
					for(int i = count;i < dataFields.size();i++) {
						data.put(dataFields.get(i), res[i]==null?"":res[i].toString());
					}
				}
				System.out.println("data***************"+data);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return data;
	}
	
}
