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
		
		final String primaryTable = tableName;
		List<String> dataFields = dataDetailFields.stream().map(field -> field.getDataFieldLocation()+"."+field.getDataFieldName()).collect(Collectors.toList());
		List<String> defaultFields = Constants.DATA_DETAIL_DEFAULT_FIELDS.stream().map(f -> primaryTable+"."+f).collect(Collectors.toList());
		dataFields.addAll(defaultFields);
		
		String sqlQuery = "select " +StringUtils.join(dataFields,',')
				+ " from  "+tableName+" "+tableName+" "
				+ " left join tab_applicant tab_applicant on tab_applicant.id ="+tableName+".applicant_id "
				+ " where "+tableName+".app_form_uid =:appFormUid "
				+ " and "+tableName+".application_uid =:applicationUid "
				+ " and "+tableName+".record_status_id =:recordStatusId "
				+ " order by "+tableName+".id ";

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
					int labelCount = 0;
					for(int i = count;i < dataFields.size();i++) {
						data.put(Constants.DATA_DETAIL_DEFAULT_FIELDS_LABELS.get(labelCount), res[i]==null?"":res[i].toString());
						labelCount ++;
					}
				}
				System.out.println("data***************"+data);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return data;
	}

	@Override
	public List<LinkedHashMap<String, Object>> findAppFormDataListByAppFormUidAndDataPathAndDataFieldsAndWorkFlowId(
			UUID appFormUid, String dataPath, List<String> dataListFields, Long workFlowId) {
		
		List<LinkedHashMap<String,Object>> listData= new ArrayList<>();
		String tableName = "tab_form_data1";
		if(dataPath.equals(Constants.DATA_PATH_2)) {
			tableName = "tab_form_data2";
		} 
		
		final String primaryTable = tableName;
		List<String> defaultFields = Constants.DATA_LIST_DEFAULT_FIELDS.stream().map(f -> primaryTable+"."+f).collect(Collectors.toList());
		dataListFields.addAll(defaultFields);
		
		List<String> fieldLabels = dataListFields.stream().map(f -> f.substring(f.indexOf('.')+1)).collect(Collectors.toList());
		
		String sqlQuery = "select " +StringUtils.join(dataListFields,',')
				+ " from  "+tableName+" "+tableName+" "
				+ " left join tab_applicant tab_applicant on tab_applicant.id ="+tableName+".applicant_id "
				+ " where "+tableName+".app_form_uid =:appFormUid "
				+ " and "+tableName+".record_status_id =:recordStatusId "
				+ " and "+tableName+".work_flow_id =:workFlowId "
				+ " order by "+tableName+".id ";

		Query q = entityManager.createNativeQuery(sqlQuery);
		q.setParameter("appFormUid", appFormUid);
		q.setParameter("workFlowId", workFlowId);
		q.setParameter("recordStatusId",Constants.RECORD_STATUS_ACTIVE);
		
		try {
			
			List<Object[]> results = q.getResultList();
			if(results != null && !results.isEmpty()) {
				
				for(Object[] res : results) {
					
					LinkedHashMap<String,Object> entry = new LinkedHashMap<>();
					
					for(int i=0;i < dataListFields.size();i++) {
						entry.put(fieldLabels.get(i), res[i]==null?"":res[i].toString());
					}
					
					listData.add(entry);
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return listData;
	}
	
}
