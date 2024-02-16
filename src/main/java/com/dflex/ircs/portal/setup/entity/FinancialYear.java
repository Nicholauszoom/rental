package com.dflex.ircs.portal.setup.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.dflex.ircs.portal.util.CommonEntity;

import jakarta.persistence.*;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Augustino Mwageni
 * 
 * Entity Class for Database Table tab_financial_year
 *
 */

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_financial_year")
public class FinancialYear extends CommonEntity implements Serializable {

	private static final long serialVersionUID = -80992527267230465L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="financial_year_id_generator")
	@SequenceGenerator(name="financial_year_id_generator", sequenceName="seq_financial_year", initialValue=1, allocationSize = 1)
	@Column(name="id", unique=true, nullable=false, precision=11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="financial_year_uid",nullable = false)
	private UUID financialYearUid;
	
	@PrePersist
    protected void onCreate() {
        setFinancialYearUid(java.util.UUID.randomUUID());
    }
	
	@Column(name="short_year", nullable=false)
	private Integer shortYear;

	@Column(name="year_start", nullable=false)
	private Date yearStart;
	
	@Column(name="year_end", nullable=false)
	private Date yearEnd;
	
	@Column(name="record_status_id", nullable=false)
	private Long recordStatusId = 1L;
}