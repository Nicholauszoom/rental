package com.dflex.ircs.portal.payment.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.dflex.ircs.portal.setup.entity.PaymentFacilitator;
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
 *         Entity class for database table tab_payment
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_payment")
public class Payment extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_id_generator")
	@SequenceGenerator(name = "payment_id_generator", sequenceName = "seq_payment", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="payment_uid",nullable = false)
	private UUID paymentUid;
	
	@PrePersist
    protected void onCreate() {
        setPaymentUid(java.util.UUID.randomUUID());
    }
	
	@Column(name = "invoice_id", nullable = false)
	private Long invoiceId;

	@Column(name = "invoice_number", length = 100, nullable = false)
	private String invoiceNumber;

	@Column(name = "invoice_payment_number", nullable = false, length = 50)
	private String invoicePaymentNumber;

	@Column(name = "payment_number", length = 100, nullable = false)
	private String paymentNumber;
	
	@Column(name = "transaction_number", length = 100, nullable = false, unique = true)
	private String transactionNumber;
	
	@Column(name = "payment_reference", length = 100, nullable = false, unique = true)
	private String paymentReference;

	@Column(name = "payer_name", length = 100, nullable = false)
	private String payerName;

	@Column(name = "payer_phone_number", length = 12)
	private String payerPhoneNumber;

	@Column(name = "payer_email", length = 50)
	private String payerEmail;
	
	@Column(name = "transaction_type_code", nullable = false)
	private String transactionTypeCode;
	
	@Column(name = "transaction_date", nullable = false)
	private Date transactionDate;

	@Column(name = "reconciled_date")
	private Date reconciledDate;

	@Column(name = "paid_amount", nullable = false)
	private BigDecimal paidPmount;
	
	@Column(name = "currency_code", length = 3)
	private String currencyCode;
	
	@Column(name = "payment_method", length = 50)
	private String paymentMethod;
	
	@Column(name = "payment_method_reference", length = 50)
	private String paymentMethodReference;

	@Column(name = "is_reconciled", nullable = false)
	private Boolean isReconciled = false;
	
	@Column(name = "payment_facilitator_code", nullable = false, length = 10)
	private String paymentFacilitatorCode;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "payment_facilitator_id", nullable = false)
	private PaymentFacilitator paymentFacilitator;
	
	@Column(name = "collection_account_number", nullable = false, length = 50)
	private String collectionAccountNumber;
	
	@Column(name = "collection_account_id", nullable = false)
	private Long collectionAccountId;
	
	@Column(name = "remark", length = 200)
	private String remark;
	
	@Column(name = "detail_count", nullable = false)
	private Integer detailCount;

	@Column(name = "request_identity", length = 100)
	private String requestIdentity;

	@Column(name = "received_date", nullable = false)
	private Date receivedDate;

	@Column(name = "process_start_date", nullable = false)
	private Date processStartdate;

	@Column(name = "process_end_date", nullable = false)
	private Date processEndDate;
	
	@Column(name="record_status_id", nullable=false)
	private Long recordStatusId = 1L;

	public Payment(Long invoiceId, String invoiceNumber,
			String invoicePaymentNumber, String paymentNumber, String transactionNumber, String paymentReference,
			String payerName, String payerPhoneNumber, String payerEmail, String transactionTypeCode,
			Date transactionDate, BigDecimal paidPmount, String currencyCode, String paymentMethod,
			String paymentMethodReference, Boolean isReconciled, String paymentFacilitatorCode,
			PaymentFacilitator paymentFacilitator, String collectionAccountNumber, Long collectionAccountId,
			String remark, Integer detailCount, String requestIdentity, Date receivedDate, Date processStartdate,
			Date processEndDate,UUID createdBy, String createdByUsername) {
		super(createdBy, createdByUsername);
		this.invoiceId = invoiceId;
		this.invoiceNumber = invoiceNumber;
		this.invoicePaymentNumber = invoicePaymentNumber;
		this.paymentNumber = paymentNumber;
		this.transactionNumber = transactionNumber;
		this.paymentReference = paymentReference;
		this.payerName = payerName;
		this.payerPhoneNumber = payerPhoneNumber;
		this.payerEmail = payerEmail;
		this.transactionTypeCode = transactionTypeCode;
		this.transactionDate = transactionDate;
		this.paidPmount = paidPmount;
		this.currencyCode = currencyCode;
		this.paymentMethod = paymentMethod;
		this.paymentMethodReference = paymentMethodReference;
		this.isReconciled = isReconciled;
		this.paymentFacilitatorCode = paymentFacilitatorCode;
		this.paymentFacilitator = paymentFacilitator;
		this.collectionAccountNumber = collectionAccountNumber;
		this.collectionAccountId = collectionAccountId;
		this.remark = remark;
		this.detailCount = detailCount;
		this.requestIdentity = requestIdentity;
		this.receivedDate = receivedDate;
		this.processStartdate = processStartdate;
		this.processEndDate = processEndDate;
	}
	
}