package com.dflex.ircs.portal.setup.entity;

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
 *         Entity class for database table tab_currency
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tab_currency")
public class Currency extends CommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_id_generator")
	@SequenceGenerator(name = "currency_id_generator", sequenceName = "seq_currency", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="currency_uid",nullable = false)
	private UUID currencyUid;
	
	@PrePersist
    protected void onCreate() {
        setCurrencyUid(java.util.UUID.randomUUID());
    }

	@Column(name = "currency_code", length = 3, nullable = false)
	private String currencyCode;

	@Column(name = "currency_name", length = 50, nullable = false)
	private String currencyName;

	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;

}