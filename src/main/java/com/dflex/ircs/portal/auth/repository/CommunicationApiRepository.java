package com.dflex.ircs.portal.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.dflex.ircs.portal.auth.entity.CommunicationApi;

/**
 * @author Augustino Mwageni
 *
 */
@Transactional
public interface CommunicationApiRepository extends JpaRepository<CommunicationApi, Long>, CommunicationApiRepositoryCustom {

}
