package com.dflex.ircs.portal.module.entity;

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
 *         Entity class for database table tab_app_form_field
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_app_form_field")
public class AppFormField extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_form_field_id_generator")
	@SequenceGenerator(name = "app_form_field_id_generator", sequenceName = "seq_app_form_field", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="app_form_field_uid",nullable = false)
	private UUID appFormFieldUid;
	
	@PrePersist
    protected void onCreate() {
        setAppFormFieldUid(java.util.UUID.randomUUID());
    }
	
	@Column(name = "validation", length = 250, nullable = true)
	private String validation;

	@Column(name = "field_type", length = 50, nullable = false)
	private String fieldType;
	
	@Column(name = "field_order", nullable = false)
	private Integer fieldOrder;
	
	@Column(name = "value_minimum_size", nullable = false)
	private Integer valueMinimumSize;
	
	@Column(name = "value_maximum_size", nullable = false)
	private Integer valueMaximumSize;
	
	@Column(name = "value_default",length = 250,nullable = true)
	private String valueDefault;
	
	@Column(name = "data_type", length = 50, nullable = false)
	private String dataType;

	@Column(name = "form_display_text", length = 100, nullable = false)
	private String formDisplayText;
	
	@Column(name = "list_display_text", length = 30, nullable = false)
	private String listDisplayText;
	
	@Column(name = "display_size_class", length = 50, nullable = false)
	private String displaySizeClass;
	
	@Column(name = "data_field_key",length=30, nullable = false)
	private String dataFieldKey;
	
	@Column(name = "data_field_name",length=30, nullable = false)
	private String dataFieldName;
	
	@Column(name = "data_field_location",length=30, nullable = false)
	private String dataFieldLocation;
	
	@Column(name = "data_source_type_id", nullable = false)
	private Long dataSourceTypeId;
	
	@Column(name = "show_on_list", nullable = false)
	private Boolean showOnList = false;
	
	@Column(name = "read_only", nullable = false)
	private Boolean readOnly = false;
	
	@Column(name = "disabled", nullable = false)
	private Boolean disabled = false;
	
	@Column(name = "is_searchable", nullable = false)
	private Boolean isSearchable = false;
	
	@Column(name = "search_data", nullable = true,length=50)
	private String searchData;
	
	@Column(name = "show_on_detail", nullable = false)
	private Boolean showOnDetail = false;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "app_lookup_type_id", nullable = true)
	private AppLookupType appLookupType;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "app_form_id", nullable = false)
	private AppForm appForm;

}