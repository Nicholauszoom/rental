package com.ircs.portal.setup.entity;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.ircs.portal.util.CommonEntity;

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
 *         Entity class for database table tab_work_station
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_work_station")
public class WorkStation extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "work_station_id_generator")
	@SequenceGenerator(name = "work_station_id_generator", sequenceName = "seq_work_station", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;

	@GeneratedValue
	@UuidGenerator
	@Column(name="work_station_uid",nullable = false)
	private UUID workStationUid;
	
	@PrePersist
    protected void onCreate() {
        setWorkStationUid(java.util.UUID.randomUUID());
	}

	@Column(name="work_station_code",nullable=false,length = 20)
	private String workStationCode;

	@Column(name="work_station_name",nullable=false,length = 50)
	private String workStationName;
	
	@Column(name="physical_address",length = 200)
	private String physicalAddress;
	
	@Column(name="service_institution_id" , nullable=false)
	private Long serviceInstitutionId;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
}
