package com.dflex.ircs.portal.module.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.dflex.ircs.portal.util.CommonEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
 *Entity class for database table tab_app_lookup_type
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_app_lookup_type")
public class AppLookupType extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_lookup_type_id_generator")
	@SequenceGenerator(name = "app_lookup_type_id_generator", sequenceName = "seq_app_lookup_type", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="app_lookup_type_uid",nullable = false)
	private UUID appLookupTypeUid;
	
	@PrePersist
    protected void onCreate() {
        setAppLookupTypeUid(java.util.UUID.randomUUID());
    }

	@Column(name = "lookup_type_name", length = 100, nullable = false)
	private String lookupTypeName;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "tab_lookup_type_lookup", joinColumns = @JoinColumn(name = "app_lookup_type_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "app_lookup_id", referencedColumnName = "id"))
	private Set<AppLookup> appLookup = new HashSet<AppLookup>();

}