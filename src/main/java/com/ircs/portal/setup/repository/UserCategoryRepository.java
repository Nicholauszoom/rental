package com.ircs.portal.setup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ircs.portal.setup.entity.UserCategory;


public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {

	public UserCategory findByUserCategoryCodeAndRecordStatusId(String serviceDepartmentCode, Long recordStatusId);

}
