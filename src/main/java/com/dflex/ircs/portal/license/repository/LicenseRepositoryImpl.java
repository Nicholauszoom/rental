package com.dflex.ircs.portal.license.repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dflex.ircs.portal.license.dto.LicenseDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Augustino Mwageni
 *
 */
@Repository
@Transactional
public class LicenseRepositoryImpl implements LicenseRepositoryCustom  {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<LicenseDto> findBySeachCriteriaAndRecordStatusId(String licenseNumber, String licenseName,
			Long recordStatusId,String applicantAccount,String applicantName,String identityNumber,Long recordlimit) {
		
		DateFormat dtmFormat = new SimpleDateFormat("dd-MMM-yyyy");
		List<LicenseDto> licenses = new ArrayList<>();
		
		try {
			String sqlQuery = "select l.id,l.license_uid,l.detail_reference_id,l.detail_reference_table,l.parent_license_id,"
					+ "l.license_type_id,t.license_type_name,l.license_number,l.license_name,a.applicant_uid,"
					+ "a.applicant_name,a.applicant_account,a.identity_number,to_char(l.issued_date,'dd-Mon-yyyy') as issued_date,"
					+ "to_char(l.commenced_date,'dd-Mon-yyyy') as commenced_date,l.tenure,to_char(l.expiry_date,'dd-Mon-yyyy') as expiry_date,"
					+ "l.license_status_id,s.license_status_name,l.has_renew_request,l.has_transfer_request,l.is_paid,"
					+ "l.previous_license_id "
					+ " from tab_license l join tab_applicant a on l.applicant_id = a.id "
					+ " join tab_license_type t on l.license_type_id = t.id "
					+ " join tab_license_status s on l.license_status_id = s.id "
					+ " where upper(l.license_name) like '%"+licenseName+"%' "
					+ " and upper(l.license_number) like '%"+licenseNumber+"%' "
					+ " and upper(a.applicant_name) like '%"+applicantName+"%' "
					+ " and upper(a.applicant_account) like '%"+applicantAccount+"%' "
					+ " and upper(a.identity_number) like '%"+identityNumber+"%' "
					+ " and a.record_status_id =:recordStatusId "
					+ " order by l.license_name limit :limit ";
			Query q = entityManager.createNativeQuery(sqlQuery);
			q.setParameter("recordStatusId", recordStatusId);
			q.setParameter("limit", recordlimit);
			
			List<Object[]> results = q.getResultList();
			if(results != null && !results.isEmpty()) {
				
				for(Object[] res : results) {
					licenses.add(new LicenseDto(Long.parseLong(res[0].toString()),
							String.valueOf(res[1]),res[2]==null?null:Long.parseLong(res[2].toString()),String.valueOf(res[3]==null?"":res[3]),
							res[4] == null?null:Long.parseLong(res[4].toString()),res[5] == null?null:Long.parseLong(res[5].toString()),
							res[6] == null?"":res[6].toString(),res[7] == null?"":res[7].toString(),res[8] == null?"":res[8].toString(),
									
							res[9] == null?null:res[9].toString(),res[10] == null?"":res[10].toString(),res[11] == null?"":res[11].toString(),
									res[12] == null?"":res[12].toString(),res[13] == null?null:dtmFormat.parse(res[13].toString()),
							res[14] == null?null:dtmFormat.parse(res[14].toString()),res[15] == null?null:Integer.parseInt(res[15].toString()),
									res[16] == null?null:dtmFormat.parse(res[16].toString()),
							
							res[17] == null?null:Long.parseLong(res[17].toString()),res[18] == null?"":res[18].toString(),
							res[19] == null?null:Boolean.valueOf(res[19].toString()),res[20] == null?null:Boolean.valueOf(res[20].toString()),
							
							res[21] == null?null:Boolean.valueOf(res[21].toString()),res[22] == null?null:Long.parseLong(res[22].toString())
									)
							);
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return licenses;
	}

}
