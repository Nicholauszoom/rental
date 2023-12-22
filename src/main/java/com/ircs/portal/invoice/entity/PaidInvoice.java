package com.ircs.portal.invoice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.ircs.portal.setup.entity.ExchangeRate;
import com.ircs.portal.setup.entity.IdentityType;
import com.ircs.portal.setup.entity.ServiceInstitution;
import com.ircs.portal.setup.entity.WorkStation;
import com.ircs.portal.util.CommonEntity;

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
 *         Entity class for database table tab_paid_invoice
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_paid_invoice")
public class PaidInvoice extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paid_invoice_id_generator")
	@SequenceGenerator(name = "paid_invoice_id_generator", sequenceName = "seq_paid_invoice", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@Column(name="invoice_uid",nullable = false)
	private UUID invoiceUid;
	
	@Column(name = "invoice_id", nullable = false)
	private Long invoiceId;

	@Column(name = "invoice_number", length = 100, nullable = false)
	private String invoiceNumber;

	@Column(name = "invoice_description", length = 500, nullable = false)
	private String invoiceDescription;

	@Column(name = "invoice_payment_number", nullable = false, length = 50, unique = true)
	private String invoicePaymentNumber;

	@Column(name = "reference", length = 100)
	private String reference;

	@Column(name = "customer_name", length = 100, nullable = false)
	private String customerName;

	@Column(name = "customer_identity", length = 50, nullable = false)
	private String customerIdentity;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "identity_type_id", nullable = false)
	private IdentityType identityType;

	@Column(name = "customer_phone_number", length = 12)
	private String customerPhoneNumber;

	@Column(name = "customer_email", length = 50)
	private String customerEmail;

	@Column(name = "customer_account", length = 50, nullable = false)
	private String customerAccount;

	@Column(name = "invoice_issue_date", nullable = false)
	private Date invoiceIssueDate;

	@Column(name = "invoice_expiry_date", nullable = false)
	private Date invoiceExpiryDate;

	@Column(name = "issued_by", nullable = false, length = 50)
	private String issuedBy;

	@Column(name = "approved_by", nullable = false, length = 50)
	private String approvedBy;
	
	@Column(name = "payment_option", nullable = false)
	private Integer paymentOption;

	@Column(name = "invoice_amount", nullable = false)
	private BigDecimal invoiceAmount;

	@Column(name = "paid_amount", nullable = false)
	private BigDecimal paidPmount;

	@Column(name = "currency_code", length = 3)
	private String currencyCode;

	@Column(name = "exchange_rate_value", nullable = false)
	private BigDecimal exchangeRateValue;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "exchange_rate_id", nullable = false)
	private ExchangeRate exchangeRate;

	@Column(name = "is_invoice_paid", nullable = false)
	private Boolean isInvoicePaid;
	
	@Column(name = "invoice_pay_plan_id", nullable = false)
	private Long invoicePayPlanId = 1L;

	@Column(name = "next_reminder")
	private Date nextReminder;

	@Column(name = "reminder_status_id", nullable = false)
	private Long reminderStatusId;

	@Column(name = "service_institution_code", nullable = false, length = 10)
	private String serviceInstitutionCode;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "service_institution_id", nullable = false)
	private ServiceInstitution serviceInstitution;

	@Column(name = "work_station_code", nullable = false, length = 20)
	private String workStationCode;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "work_station_id", nullable = false)
	private WorkStation workStation;

	@Column(name = "request_identity", length = 100)
	private String requestIdentity;

	@Column(name = "received_date", nullable = false)
	private Date receivedDate;

	@Column(name = "process_start_date", nullable = false)
	private Date processStartdate;

	@Column(name = "process_end_date", nullable = false)
	private Date processEndDate;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
}