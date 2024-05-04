package com.dflex.ircs.portal.rental.entity;

import com.dflex.ircs.portal.application.entity.Applicant;
import com.dflex.ircs.portal.payer.entity.Payer;
import com.dflex.ircs.portal.util.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_rental_application")
public class RentalApplication implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE,
                generator = "rental_application_id_generator")
        @SequenceGenerator(name = "rental_application_id_generator",
                sequenceName = "seq_rental_application", initialValue = 1,
                allocationSize = 1)
        @Column(name = "id", nullable = false, precision = 11)
        private Long id;

        @GeneratedValue
        @UuidGenerator
        @Column(name="rental_application_uid",nullable = false)
        private UUID rentalApplicationUid;

        @PrePersist
        protected void onCreate() {

                setRentalApplicationUid(java.util.UUID.randomUUID());
        }

        @Enumerated(EnumType.STRING)
        private Status status;

        @ManyToOne(fetch = FetchType.EAGER, optional = false)
        @JoinColumn(name = "applicant_reference_number", nullable = false)
        private Applicant applicant;

        @Column(name = "approver_name")
        private String approverName;

        @Column(name = "amount")
        private BigDecimal amount;

        @OneToOne
        @JoinColumn(name = "unit_id")
        private Unit unit;

        @ManyToOne(fetch = FetchType.EAGER, optional = false)
        @JoinColumn(name = "building_id", nullable = false)
        private Building building;



}
