package com.dflex.ircs.portal.rental.entity;

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
@Table(name = "tab_rate")
public class Rate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "rate_id_generator")
    @SequenceGenerator(name = "rate_id_generator",
            sequenceName = "seq_rate", initialValue = 1, allocationSize = 1)
    @Column(name = "id", nullable = false, precision = 11)
    private Long id;

    @GeneratedValue
    @UuidGenerator
    @Column(name="rate_uid",nullable = false)
    private UUID rateUid;

    @PrePersist
    protected void onCreate() {

        setRateUid(java.util.UUID.randomUUID());
    }

    @Column(name = "price_type", length = 64, nullable = false)
    private String priceType ;

    @Column(name = "dynamic_price", nullable = false)
    private BigDecimal dynamicPrice;

    @Column(name = "fixed_price", nullable = false)
    private BigDecimal fixedPrice ;

    @OneToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @OneToOne
    @JoinColumn(name = "building_number")
    private Building building;


}
