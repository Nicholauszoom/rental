package com.dflex.ircs.portal.setup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.dflex.ircs.portal.setup.entity.ApiCategory;



/**
 * @author Augustino Mwageni
 *
 */
@Transactional
public interface ApiCategoryRepository extends JpaRepository<ApiCategory, Long> {

}
