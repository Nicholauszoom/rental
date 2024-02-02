package com.dflex.ircs.portal.payer.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "payment_service_provider",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"location_id", "contact_id"})
        })
@SequenceGenerator(name = "payment_service_provider_seq", sequenceName = "payment_service_provider_seq", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "payment_service_provider_id"))

public class PaymentServiceProvider implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

}
