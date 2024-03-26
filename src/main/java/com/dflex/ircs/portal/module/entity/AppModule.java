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
 *         Entity class for database table tab_app_module
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_app_module")
public class AppModule extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_module_id_generator")
	@SequenceGenerator(name = "app_module_id_generator", sequenceName = "seq_app_module", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="app_module_uid",nullable = false)
	private UUID appModuleUid;
	
	@PrePersist
    protected void onCreate() {
        setAppModuleUid(java.util.UUID.randomUUID());
    }

	@Column(name = "module_name", length = 100, nullable = false)
	private String moduleName;

	@Column(name = "module_description", length = 200, nullable = true)
	private String moduleDescription;
	
	@Column(name = "display_label", length = 200, nullable = true)
	private String displayLabel;
	
	@Column(name = "display_on_menu", nullable = false)
	private Boolean displayOnMenu = true;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;

}