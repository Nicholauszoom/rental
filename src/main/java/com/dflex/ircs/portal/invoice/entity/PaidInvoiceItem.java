package com.dflex.ircs.portal.invoice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import com.dflex.ircs.portal.setup.entity.ServiceTypeSource;
import com.dflex.ircs.portal.util.CommonEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
 *         Entity class for database table tab_paid_invoice_item
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_paid_invoice_item")
public class PaidInvoiceItem extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paid_invoice_item_id_generator")
	@SequenceGenerator(name = "paid_invoice_item_id_generator", sequenceName = "seq_paid_invoice_item", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@Column(name="invoice_item_uid",nullable = false)
	private UUID invoiceItemUid;
	
	@Column(name = "invoice_item_id",nullable = false)
	private Long invoiceItemId;

	@Column(name = "service_department_code", length = 20, nullable = false)
	private String serviceDepartmentCode;

	@Column(name = "service_type_code", length = 20, nullable = false)
	private String serviceTypeCode;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "service_type_source_id", nullable = false)
	private ServiceTypeSource serviceTypeSource;

	@Column(name = "service_reference", length = 200, nullable = true)
	private String serviceReference;
	
	@Column(name = "service_amount",nullable = false)
	private BigDecimal serviceAmount;
	
	@Column(name = "paid_invoice_id",nullable = false)
	private Long paidInvoiceId;
	
	@Column(name = "payment_priority",nullable = false)
	private Integer paymentPriority;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;

}