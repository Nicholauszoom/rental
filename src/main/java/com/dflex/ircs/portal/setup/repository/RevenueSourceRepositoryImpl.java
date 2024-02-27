package com.dflex.ircs.portal.setup.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dflex.ircs.portal.setup.dto.RevenueSourceDetailsDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Augustino Mwageni
 *
 */
@Repository
@Transactional
public class RevenueSourceRepositoryImpl implements RevenueSourceRepositoryCustom  {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<RevenueSourceDetailsDto> findDetailsByServiceInstitutionUidAndAppModuleUidAndRecordStatusId(
			UUID serviceInstitutionUid, UUID appModuleUid, Long recordStatusId) {
		
		List<RevenueSourceDetailsDto> revenueSources = null;

		String sqlQuery = "select r.id,r.revenue_source_uid,r.is_fixed_amount,r.fixed_amount,app_module_uid, "
				+ "s.service_type_uid,s.service_type_name,s.service_type_code,s.service_type_display_text, c.currency_uid,c.currency_name, "
				+ "c.currency_code, w.revenue_source_work_flow_uid,w.work_flow_number,a.app_form_uid, f.work_flow_name ,"
				+ "r.is_default_revenue_source "
				+ "from tab_revenue_source r "
				+ "join tab_app_module m on m.id = r.app_module_id "
				+ "join tab_service_type s on r.service_type_id = s.id "
				+ "join tab_service_department d on r.service_department_id = d.id "
				+ "join tab_service_institution si on si.id = d.service_institution_id "
				+ "left join tab_currency c on c.id = r.currency_id "
				+ "left join tab_revenue_source_work_flow w on r.id = w.revenue_source_id "
				+ "join tab_work_flow f on f.id = w.work_flow_id "
				+ "join tab_app_form a on a.id = w.app_form_id "
				+ "where w.is_default_work_flow = 't' "
				+ "and d.service_institution_uid =: serviceInstitutionUid "
				+ "and m.app_module_uid =: appModuleUid "
				+ "and r.record_status_id =:recordStatusId";
		Query q = entityManager.createNativeQuery(sqlQuery);
		q.setParameter("serviceInstitutionUid", serviceInstitutionUid);
		q.setParameter("appModuleUid", appModuleUid);
		q.setParameter("recordStatusId", recordStatusId);
		
		
		List<Object[]> results = q.getResultList();
		if(results != null && !results.isEmpty()) {
			
			revenueSources = new ArrayList<>();
			for(Object[] res : results) {
				
				revenueSources.add(new RevenueSourceDetailsDto(
						Long.parseLong(res[0].toString()),
						res[1].toString(),
						Boolean.valueOf(res[2].toString()),
						new BigDecimal(res[3] == null?"0.00":res[3].toString()),
						res[4].toString(),
						
						res[5].toString(),
						res[6].toString(),
						res[7].toString(),
						res[8] == null?"":res[8].toString(),
						res[9] == null?"":res[9].toString(),
						res[10] == null?"":res[10].toString(),
						
						res[11] == null?"":res[11].toString(),
						res[12].toString(),
						Integer.parseInt(res[13].toString()),
						res[14].toString(),
						res[15].toString(),
						
						Boolean.valueOf(res[16].toString())
						));
			}
		}
		
		return revenueSources;
	}

	@Override
	public List<RevenueSourceDetailsDto> findDetailsByServiceDepartmentUidAndAppModuleUidAndRecordStatusId(
			UUID departmentUid, UUID moduleUid, Long recordStatusId) {
		List<RevenueSourceDetailsDto> revenueSources = null;

		String sqlQuery = "select r.id,r.revenue_source_uid,r.is_fixed_amount,r.fixed_amount,app_module_uid, "
				+ "s.service_type_uid,s.service_type_name,s.service_type_code,s.service_type_display_text,c.currency_uid,c.currency_name, "
				+ "c.currency_code, w.revenue_source_work_flow_uid,w.work_flow_number,f.work_flow_name,a.app_form_uid,"
				+ "r.is_default_revenue_source "
				+ "from tab_revenue_source r "
				+ "join tab_app_module m on m.id = r.app_module_id "
				+ "join tab_service_type s on r.service_type_id = s.id "
				+ "join tab_service_department d on r.service_department_id = d.id "
				+ "left join tab_currency c on c.id = r.currency_id "
				+ "left join tab_revenue_source_work_flow w on r.id = w.revenue_source_id "
				+ "join tab_work_flow f on f.id = w.work_flow_id "
				+ "join tab_app_form a on a.id = w.app_form_id "
				+ "where w.is_default_work_flow = 't' "
				+ "and d.service_department_uid =:departmentUid "
				+ "and m.app_module_uid =:moduleUid "
				+ "and r.record_status_id =:recordStatusId";
		Query q = entityManager.createNativeQuery(sqlQuery);
		q.setParameter("departmentUid", departmentUid);
		q.setParameter("moduleUid", moduleUid);
		q.setParameter("recordStatusId", recordStatusId);
		
		
		List<Object[]> results = q.getResultList();
		if(results != null && !results.isEmpty()) {
			
			revenueSources = new ArrayList<>();
			for(Object[] res : results) {
				
				revenueSources.add(new RevenueSourceDetailsDto(
					Long.parseLong(res[0].toString()),
					res[1].toString(),
					Boolean.valueOf(res[2].toString()),
					new BigDecimal(res[3] == null?"0.00":res[3].toString()),
					res[4].toString(),
					
					res[5].toString(),
					res[6].toString(),
					res[7].toString(),
					res[8] == null?"":res[8].toString(),
					res[9] == null?"":res[9].toString(),
					res[10] == null?"":res[10].toString(),
					
					res[11] == null?"":res[11].toString(),
					res[12].toString(),
					Integer.parseInt(res[13].toString()),
					res[14].toString(),
					res[15].toString(),
					
					Boolean.valueOf(res[16]==null?"f":res[16].toString())
					));
			}
		}
		
		return revenueSources;
	}
	
}
