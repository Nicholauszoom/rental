package com.ircs.portal.util;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;

/**
 * 
 * @author Augustino Mwageni
 * Common entity extended by each entity class
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@MappedSuperclass
public class CommonEntity {
	
	@Column(name="created_date", nullable=false,updatable = false)
	@CreationTimestamp
	private Date createdDate;
	
	@Column(name="updated_date", nullable=true)
	private Date updatedDate;

	@Column(name="created_by", nullable=false,updatable = false)
	private UUID createdBy;
	
	@Column(name="created_by_username", nullable=false,updatable = false)
	private String createdByUsername;
	
	@Column(name="updated_by", nullable=true)
	private UUID updatedBy;
	
	@Column(name="updated_by_username", nullable=true,updatable = true)
	private Long updatedByUsername;

	public CommonEntity(UUID createdBy, String createdByUsername) {
		super();
		this.createdBy = createdBy;
		this.createdByUsername = createdByUsername;
	}
}
