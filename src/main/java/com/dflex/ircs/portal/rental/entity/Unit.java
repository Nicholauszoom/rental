package com.dflex.ircs.portal.rental.entity;

import com.dflex.ircs.portal.payer.entity.Payer;
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
@Table(name = "tab_unit")
public class Unit  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_id_generator")
    @SequenceGenerator(name = "unit_id_generator", sequenceName = "seq_unit", initialValue = 1, allocationSize = 1)
    @Column(name = "id", nullable = false, precision = 11)
    private Long id;

    @GeneratedValue
    @UuidGenerator
    @Column(name="unit_uid",nullable = false)
    private UUID unitUid;
    @PrePersist
    protected void onCreate() {
        setUnitUid(java.util.UUID.randomUUID());
    }

    @Column(name = "unit_number", nullable = false)
    private String unitNumber;

    @Column(name = "unit_name", length = 100, nullable = false)
    private String unitName;

    @Column(name = "unit_size", length = 100, nullable = false)
    private String unitSize;

    @Column(name = "type_size", length = 100, nullable = false)
    private String typeSize;

    @Column(name = "status", length = 100, nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "payer_id", nullable = false)
    private Payer payer;

    @OneToOne(mappedBy = "unit")
    private Rate rate;

//    @ManyToOne(fetch = FetchType.EAGER, optional = false)
//    @JoinColumn(name = "status_id", nullable = false)
//    private Status status;

}
