package com.dflex.ircs.portal.data.entity;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.dflex.ircs.portal.util.CommonEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
 *         Entity class for database table tab_app_form_data_table_field
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_app_form_data_table_field")
public class AppFormDataTableField extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_form_data_table_field_id_generator")
	@SequenceGenerator(name = "app_form_data_table_field_id_generator", sequenceName = "seq_app_form_data_table_field", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="app_form_data_table_field_uid",nullable = false)
	private UUID appFormDataTableFieldUid;
	
	@PrePersist
    protected void onCreate() {
        setAppFormDataTableFieldUid(java.util.UUID.randomUUID());
    }

	@Column(name = "data_table_field_name", length = 100, nullable = false)
	private String dataTableFieldName;
	
	@Column(name = "field_status_id", nullable = false)
	private Long fieldStatusId = 1L;

	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "app_form_data_table_id", nullable = false)
	private AppFormDataTable appFormDataTable;
	
}