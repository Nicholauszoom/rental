package com.dflex.ircs.portal.module.entity;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.dflex.ircs.portal.util.CommonEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Augustino Mwageni
 *
 *         Entity class for database table tab_app_lookup
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_app_lookup")
public class AppLookup extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_lookup_id_generator")
	@SequenceGenerator(name = "app_lookup_id_generator", sequenceName = "seq_app_lookup", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="app_lookup_uid",nullable = false)
	private UUID appLookupUid;
	
	@PrePersist
    protected void onCreate() {
        setAppLookupUid(java.util.UUID.randomUUID());
    }

	@Column(name = "lookup_key", nullable = false,length = 100)
	private String lookupKey;

	@Column(name = "lookup_value", length = 100, nullable = false)
	private String lookupValue;
	
	@Column(name = "app_lookup_type_id", nullable = false)
	private Long appLookupTypeId;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;

}