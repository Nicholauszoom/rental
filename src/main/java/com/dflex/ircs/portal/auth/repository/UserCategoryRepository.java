package com.dflex.ircs.portal.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.auth.entity.UserCategory;


public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {

	public UserCategory findByUserCategoryCodeAndRecordStatusId(String serviceDepartmentCode, Long recordStatusId);

}
