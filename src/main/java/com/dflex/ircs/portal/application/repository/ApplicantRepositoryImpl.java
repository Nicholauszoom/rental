package com.dflex.ircs.portal.application.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dflex.ircs.portal.application.dto.ApplicantDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * @author Augustino Mwageni
 *
 */
@Repository
@Transactional
public class ApplicantRepositoryImpl implements ApplicantRepositoryCustom  {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<ApplicantDto> findBySeachCriteriaAndRecordStatusId(String applicantAccount, String applicantName,
			String telephoneNumber, String identityNumber, String emailAddress, Long recordStatusId,Long limit) {
		
		List<ApplicantDto> applicants = new ArrayList<>();
		
		String sqlQuery = " select a.id,a.applicant_uid,a.applicant_account,a.applicant_name,a.identity_number,a.identity_type_id,"
				+ "a.identity_type_name,a.nationality,a.telephone_number,a.mobile_number,a.whatsapp_number,"
				+ "a.email_address,a.postal_address,a.plot_number,a.street,a.ward,"
				+ "a.location,a.gender_id,a.gender_name,a.location_latitude,a.location_longitude,"
				+ "a.block_code,a.block_number,a.record_status_id "
				+ " from tab_applicant a where upper(a.applicant_account) like '%"+applicantAccount+"%' "
				+ " and upper(a.applicant_name) like '%"+applicantName+"%' "
				+ " and a.telephone_number like '%"+telephoneNumber+"%' "
				+ " and upper(a.identity_number) like '%"+identityNumber+"%' "
				+ " and upper(a.email_address) like '%"+emailAddress+"%' "
				+ " and a.record_status_id =:recordStatusId "
				+ " order by a.applicant_name limit :limit ";
		Query q = entityManager.createNativeQuery(sqlQuery);
		q.setParameter("recordStatusId", recordStatusId);
		q.setParameter("limit", limit);
		
		List<Object[]> results = q.getResultList();
		if(results != null && !results.isEmpty()) {
			
			for(Object[] res : results) {
				applicants.add(new ApplicantDto(Long.parseLong(res[0].toString()),
						String.valueOf(res[1]),String.valueOf(res[2]),String.valueOf(res[3]),
						res[4] == null?"":res[4].toString(),res[5] == null?null:Long.parseLong(res[5].toString()),
						res[6] == null?"":res[6].toString(),res[7] == null?"":res[7].toString(),res[8] == null?"":res[8].toString(),
						res[9] == null?"":res[9].toString(),res[10] == null?"":res[10].toString(),res[11] == null?"":res[11].toString(),
						res[12] == null?"":res[12].toString(),res[13] == null?"":res[13].toString(),res[14] == null?"":res[14].toString(),
						res[15] == null?"":res[15].toString(),res[16] == null?"":res[16].toString(),
						res[17] == null?null:Long.parseLong(res[17].toString()),res[18] == null?"":res[18].toString(),
						res[19] == null?null:Float.parseFloat(res[19].toString()),res[20] == null?null:Float.parseFloat(res[20].toString()),
						res[21] == null?"":res[21].toString(),res[22] == null?"":res[22].toString(),res[22] == null?null:Long.parseLong(res[22].toString())));
			}
		}
		return applicants;
	}

}
