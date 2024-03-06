package com.dflex.ircs.portal.data.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Augustino Mwageni
 * Data Field class for all data tables
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@MappedSuperclass
public class DataField {
	
	/**
	 * Long Fields
	 */
	@Column(name="long_field1")
	private Long longField1;

	@Column(name="long_field2")
	private Long longField2;
	
	@Column(name="long_field3")
	private Long longField3;
	
	@Column(name="long_field4")
	private Long longField4;
	
	@Column(name="long_field5")
	private Long longField5;
	
	@Column(name="long_field6")
	private Long longField6;
	
	@Column(name="long_field7")
	private Long longField7;
	
	@Column(name="long_field8")
	private Long longField8;
	
	@Column(name="long_field9")
	private Long longField9;
	
	@Column(name="long_field10")
	private Long longField10;
	
	/**
	 * UUID Fields
	 */
	@Column(name="uid_field1",nullable=true)
	private UUID uidField1;
	
	@PrePersist
    protected void onCreate() {
        setUidField1(java.util.UUID.randomUUID());
    }
	
	/**
	 * Integer Fields
	 */
	@Column(name="integer_field1",nullable=true)
	private Integer integerField1;

	@Column(name="integer_field2",nullable=true)
	private Integer integerField2;
	
	@Column(name="integer_field3",nullable=true)
	private Integer integerField3;
	
	@Column(name="integer_field4",nullable=true)
	private Integer integerField4;
	
	@Column(name="integer_field5",nullable=true)
	private Integer integerField5;
	
	/**
	 * Decimal Fields
	 */
	@Column(name="decimal_field1",nullable=true)
	private BigDecimal decimalField1;

	@Column(name="decimal_field2",nullable=true)
	private BigDecimal decimalField2;
	
	@Column(name="decimal_field3",nullable=true)
	private BigDecimal decimalField3;
	
	@Column(name="decimal_field4",nullable=true)
	private BigDecimal decimalField4;
	
	@Column(name="decimal_field5",nullable=true)
	private BigDecimal decimalField5;
	
	@Column(name="decimal_field6",nullable=true)
	private BigDecimal decimalField6;
	
	@Column(name="decimal_field7",nullable=true)
	private BigDecimal decimalField7;
	
	@Column(name="decimal_field8",nullable=true)
	private BigDecimal decimalField8;
	
	@Column(name="decimal_field9",nullable=true)
	private BigDecimal decimalField9;
	
	@Column(name="decimal_field10",nullable=true)
	private BigDecimal decimalField10;
	
	/**
	 * Boolean Fields
	 */
	@Column(name="boolean_field1",nullable=true)
	private Boolean booleanField1;

	@Column(name="boolean_field2",nullable=true)
	private Boolean booleanField2;
	
	@Column(name="boolean_field3",nullable=true)
	private Boolean booleanField3;
	
	@Column(name="boolean_field4",nullable=true)
	private Boolean booleanField4;
	
	@Column(name="boolean_field5",nullable=true)
	private Boolean booleanField5;
	
	@Column(name="boolean_field6",nullable=true)
	private Boolean booleanField6;
	
	@Column(name="boolean_field7",nullable=true)
	private Boolean booleanField7;
	
	@Column(name="boolean_field8",nullable=true)
	private Boolean booleanField8;
	
	@Column(name="boolean_field9",nullable=true)
	private Boolean booleanField9;
	
	@Column(name="boolean_field10",nullable=true)
	private Boolean booleanField10;
	
	/**
	 * Short String Fields
	 */
	@Column(name="short_string_field1",length=50,nullable=true)
	private String shortStringField1;

	@Column(name="short_string_field2",length=50,nullable=true)
	private String shortStringField2;
	
	@Column(name="short_string_field3",length=50,nullable=true)
	private String shortStringField3;
	
	@Column(name="short_string_field4",length=50,nullable=true)
	private String shortStringField4;
	
	@Column(name="short_string_field5",length=50,nullable=true)
	private String shortStringField5;
	
	@Column(name="short_string_field6",length=50,nullable=true)
	private String shortStringField6;
	
	@Column(name="short_string_field7",length=50,nullable=true)
	private String shortStringField7;
	
	@Column(name="short_string_field8",length=50,nullable=true)
	private String shortStringField8;
	
	@Column(name="short_string_field9",length=50,nullable=true)
	private String shortStringField9;
	
	@Column(name="short_string_field10",length=50,nullable=true)
	private String shortStringField10;
	
	@Column(name="short_string_field11",length=50,nullable=true)
	private String shortStringField11;
	
	@Column(name="short_string_field12",length=50,nullable=true)
	private String shortStringField12;
	
	@Column(name="short_string_field13",length=50,nullable=true)
	private String shortStringField13;
	
	@Column(name="short_string_field14",length=50,nullable=true)
	private String shortStringField14;
	
	@Column(name="short_string_field15",length=50,nullable=true)
	private String shortStringField15;
	
	@Column(name="short_string_field16",length=50,nullable=true)
	private String shortStringField16;
	
	@Column(name="short_string_field17",length=50,nullable=true)
	private String shortStringField17;
	
	@Column(name="short_string_field18",length=50,nullable=true)
	private String shortStringField18;
	
	@Column(name="short_string_field19",length=50,nullable=true)
	private String shortStringField19;
	
	@Column(name="short_string_field20",length=50,nullable=true)
	private String shortStringField20;
	
	/**
	 * Standard String Fields
	 */
	@Column(name="standard_string_field1",length=250,nullable=true)
	private String standardStringField1;

	@Column(name="standard_string_field2",length=250,nullable=true)
	private String standardStringField2;
	
	@Column(name="standard_string_field3",length=250,nullable=true)
	private String standardStringField3;
	
	@Column(name="standard_string_field4",length=250,nullable=true)
	private String standardStringField4;
	
	@Column(name="standard_string_field5",length=250,nullable=true)
	private String standardStringField5;
	
	@Column(name="standard_string_field6",length=250,nullable=true)
	private String standardStringField6;
	
	@Column(name="standard_string_field7",length=250,nullable=true)
	private String standardStringField7;
	
	@Column(name="standard_string_field8",length=250,nullable=true)
	private String standardStringField8;
	
	@Column(name="standard_string_field9",length=250,nullable=true)
	private String standardStringField9;
	
	@Column(name="standard_string_field10",length=250,nullable=true)
	private String standardStringField10;
	
	@Column(name="standard_string_field11",length=250,nullable=true)
	private String standardStringField11;
	
	@Column(name="standard_string_field12",length=250,nullable=true)
	private String standardStringField12;
	
	@Column(name="standard_string_field13",length=250,nullable=true)
	private String standardStringField13;
	
	@Column(name="standard_string_field14",length=250,nullable=true)
	private String standardStringField14;
	
	@Column(name="standard_string_field15",length=250,nullable=true)
	private String standardStringField15;
	
	@Column(name="standard_string_field16",length=250,nullable=true)
	private String standardStringField16;
	
	@Column(name="standard_string_field17",length=250,nullable=true)
	private String standardStringField17;
	
	@Column(name="standard_string_field18",length=250,nullable=true)
	private String standardStringField18;
	
	@Column(name="standard_string_field19",length=250,nullable=true)
	private String standardStringField19;
	
	@Column(name="standard_string_field20",length=250,nullable=true)
	private String standardStringField20;
	
	/**
	 * Large String Fields
	 */
	@Column(name="large_string_field1",length=1000,nullable=true)
	private String largeStringField1;

	@Column(name="large_string_field2",length=1000,nullable=true)
	private String largeStringField2;
	
	@Column(name="large_string_field3",length=1000,nullable=true)
	private String largeStringField3;
	
	@Column(name="large_string_field4",length=1000,nullable=true)
	private String largeStringField4;
	
	@Column(name="large_string_field5",length=1000,nullable=true)
	private String largeStringField5;
	
	@Column(name="large_string_field6",length=1000,nullable=true)
	private String largeStringField6;
	
	@Column(name="large_string_field7",length=1000,nullable=true)
	private String largeStringField7;
	
	@Column(name="large_string_field8",length=1000,nullable=true)
	private String largeStringField8;
	
	@Column(name="large_string_field9",length=1000,nullable=true)
	private String largeStringField9;
	
	@Column(name="large_string_field10",length=1000,nullable=true)
	private String largeStringField10;
	
	/**
	 * Common
	 */
	@Column(name="app_form_uid", nullable=true)
	private UUID appFormUid;
	
	@Column(name="app_form_id", nullable=true)
	private Long appFormId;
	
	@Column(name="created_date", nullable=false,updatable = false)
	@CreationTimestamp
	private Date createdDate;
	
	@Column(name="updated_date", nullable=true)
	private Date updatedDate;

	@Column(name="created_by", nullable=false,updatable = false)
	private UUID createdBy;
	
	@Column(name="created_by_user_name", nullable=false,updatable = false)
	private String createdByUserName;
	
	@Column(name="updated_by", nullable=true)
	private UUID updatedBy;
	
	@Column(name="updated_by_user_name", nullable=true,updatable = true)
	private String updatedByUserName;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
	
	@Column(name = "work_flow_id", nullable = true)
	private Long workFlowId;
	
	@Column(name = "work_flow_name", nullable = true)
	private String workFlowName;
}
