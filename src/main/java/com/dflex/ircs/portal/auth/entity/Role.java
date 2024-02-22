package com.dflex.ircs.portal.auth.entity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tab_role")
public class Role extends CommonEntity implements Serializable{

	private static final long serialVersionUID = -3492438296513455891L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_generator")
	@SequenceGenerator(name = "role_id_generator", sequenceName = "seq_role", initialValue=1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="role_uid",nullable = false)
	private UUID roleUid;
	
	@PrePersist
    protected void onCreate() {
        setRoleUid(java.util.UUID.randomUUID());
	}

	@Column(name="role_description",length = 100)
	private String roleDescription;

	@Column(name="role_name",nullable = false, length = 100)
	private String roleName;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "tab_role_permission", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
	private Set<Permission> permission = new HashSet<Permission>();

	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
}