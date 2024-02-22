package com.dflex.ircs.portal.auth.entity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tab_permission")
public class Permission extends CommonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_id_generator")
	@SequenceGenerator(name = "permission_id_generator", sequenceName = "seq_permission", initialValue=1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id; 
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="permission_uid",nullable = false)
	private UUID permissionUid;
	
	@PrePersist
    protected void onCreate() {
        setPermissionUid(java.util.UUID.randomUUID());
	}
	
	@Column(name="permission_description",length = 100)
	private String permissionDescription;

	@Column(name="permission_name",nullable = false, length = 100)
	private String permissionName;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
}