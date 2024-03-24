package com.dflex.ircs.portal.setup.entity;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

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
 *         Entity class for database table tab_collection_account
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tab_collection_account")
public class CollectionAccount extends CommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "collection_account_id_generator")
	@SequenceGenerator(name = "collection_account_id_generator", sequenceName = "seq_collection_account", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="collection_account_uid",nullable = false)
	private UUID collectionAccountUid;
	
	@PrePersist
    protected void onCreate() {
        setCollectionAccountUid(java.util.UUID.randomUUID());
    }

	@Column(name = "collection_account_number", length = 50, nullable = false)
	private String collectionAccountNumber;

	@Column(name = "collection_account_name", length = 150, nullable = false)
	private String collectionAccountName;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "currency_id", nullable = false)
	private Currency currency;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "service_institution_id", nullable = false)
	private ServiceInstitution serviceInstitution;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "payment_facilitator_id", nullable = false)
	private PaymentFacilitator paymentFacilitator;

}