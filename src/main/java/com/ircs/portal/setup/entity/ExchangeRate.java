package com.ircs.portal.setup.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.ircs.portal.util.CommonEntity;

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
 *         Entity class for database table tab_exchange_rate
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tab_exchange_rate")
public class ExchangeRate extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exchange_rate_id_generator")
	@SequenceGenerator(name = "exchange_rate_id_generator", sequenceName = "seq_exchange_rate", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="exchange_rate_uid",nullable = false)
	private UUID exchangeRateUid;
	
	@PrePersist
    protected void onCreate() {
        setExchangeRateUid(java.util.UUID.randomUUID());
	}

	@Column(name = "exchange_rate_amount")
	private Double exchangeRateAmount;

	@Column(name = "rate_date")
	private Date rateDate;

	@Column(name = "service_institution_id", nullable = false)
	private Long serviceInstitutionId;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "currency_id", nullable = false)
	private Currency currency;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;

}