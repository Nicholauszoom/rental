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
 *         Entity class for database table tab_payment_facilitator
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_payment_facilitator")
public class PaymentFacilitator  extends CommonEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_facilitator_id_generator")
	@SequenceGenerator(name = "payment_facilitator_id_generator", sequenceName = "seq_payment_facilitator", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name = "payment_facilitator_uid", nullable = false)
	private UUID paymentFacilitatorUid;

	@PrePersist
	protected void onCreate() {
		setPaymentFacilitatorUid(java.util.UUID.randomUUID());
	}

	@Column(name = "payment_facilitator_code", length = 10, nullable = false)
	private String paymentFacilitatorCode;

	@Column(name = "payment_facilitator_name", length = 150, nullable = false)
	private String paymentFacilitatorName;

	@Column(name = "payment_facilitator_short_name", length = 50, nullable = false)
	private String paymentFacilitatorShortName;
	
	@Column(name="record_status_id", nullable=false)
	private Long recordStatusId = 1L;

}