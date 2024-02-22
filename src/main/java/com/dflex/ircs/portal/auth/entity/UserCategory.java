package com.dflex.ircs.portal.auth.entity;

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
 *         Entity class for database table tab_user_category
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_user_category")
public class UserCategory extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_category_id_generator")
	@SequenceGenerator(name = "user_category_id_generator", sequenceName = "seq_user_category", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="user_category_uid",nullable = false)
	private UUID userCategoryUid;
	
	@PrePersist
    protected void onCreate() {
        setUserCategoryUid(java.util.UUID.randomUUID());
	}
	
	@Column(name="user_category_code",nullable=false,length = 20)
	private String userCategoryCode;

	@Column(name="user_category_name",nullable=false,length = 50)
	private String userCategoryName;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
}