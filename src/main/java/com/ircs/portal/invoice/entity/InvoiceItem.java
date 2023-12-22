package com.ircs.portal.invoice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.ircs.portal.setup.entity.ServiceTypeSource;
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
 *         Entity class for database table tab_invoice_item
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_invoice_item")
public class InvoiceItem extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_item_id_generator")
	@SequenceGenerator(name = "invoice_item_id_generator", sequenceName = "seq_invoice_item", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="invoice_item_uid",nullable = false)
	private UUID invoiceItemUid;
	
	@PrePersist
    protected void onCreate() {
        setInvoiceItemUid(java.util.UUID.randomUUID());
    }

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
	
	@Column(name = "invoice_id",nullable = false)
	private Long invoiceId;
	
	@Column(name = "payment_priority",nullable = false)
	private Integer paymentPriority;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;

	public InvoiceItem(String serviceDepartmentCode, String serviceTypeCode, ServiceTypeSource serviceTypeSource,
			String serviceReference, BigDecimal serviceAmount, Long invoiceId, Integer paymentPriority) {
		super();
		this.serviceDepartmentCode = serviceDepartmentCode;
		this.serviceTypeCode = serviceTypeCode;
		this.serviceTypeSource = serviceTypeSource;
		this.serviceReference = serviceReference;
		this.serviceAmount = serviceAmount;
		this.invoiceId = invoiceId;
		this.paymentPriority = paymentPriority;
	}

}