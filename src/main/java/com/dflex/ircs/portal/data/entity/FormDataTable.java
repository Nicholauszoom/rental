package com.dflex.ircs.portal.data.entity;

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
 *         Entity class for database table tab_form_data_table
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_form_data_table")
public class FormDataTable extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "form_data_table_id_generator")
	@SequenceGenerator(name = "form_data_table_id_generator", sequenceName = "seq_form_data_table", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="form_data_table_uid",nullable = false)
	private UUID formDataTableUid;
	
	@PrePersist
    protected void onCreate() {
        setFormDataTableUid(java.util.UUID.randomUUID());
    }

	@Column(name = "form_data_table_name", length = 100, nullable = false)
	private String formDataTableName;
	
	@Column(name = "form_data_table_description", length = 200, nullable = true)
	private String formDataTableDescription;

	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
	
}