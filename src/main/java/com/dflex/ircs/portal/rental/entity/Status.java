package com.dflex.ircs.portal.rental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_status")
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "status_id_generator")
    @SequenceGenerator(name = "status_id_generator", sequenceName = "seq_status", initialValue = 1, allocationSize = 1)
    @Column(name = "id", nullable = false, precision = 11)
    private Long id;

    @GeneratedValue
    @UuidGenerator
    @Column(name="status_uid",nullable = false)
    private UUID statusUid;

    @PrePersist
    protected void onCreate() {

        setStatusUid(java.util.UUID.randomUUID());
    }

    @Column(name = "status_name", length = 64, nullable = false)
    private String statusName;




}
