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
@Table(name = "tab_approval")
public class Approval implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "approval_id_generator")
    @SequenceGenerator(name = "approval_id_generator",
            sequenceName = "seq_approval",
            initialValue = 1, allocationSize = 1)
    @Column(name = "id", nullable = false, precision = 11)
    private Long id;

    @GeneratedValue
    @UuidGenerator
    @Column(name="approval_uid",nullable = false)
    private UUID approvalUid;

    @PrePersist
    protected void onCreate() {
        setApprovalUid(java.util.UUID.randomUUID());
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "rental_application_id", nullable = false)
    private RentalApplication rentalApplication;

}
