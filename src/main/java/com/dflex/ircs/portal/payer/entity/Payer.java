package com.dflex.ircs.portal.payer.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "payer",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"location_id", "contact_id"})
})
@SequenceGenerator(name = "payer_seq", sequenceName = "payer_seq", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "payer_id"))
public class Payer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "code")
    private String code;

    @Column(name = "status")
    private String status;

    @Column(name = "payer_id_type")
    private String payerIdType;

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
