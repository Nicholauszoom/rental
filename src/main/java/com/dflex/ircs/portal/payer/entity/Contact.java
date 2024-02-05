package com.dflex.ircs.portal.payer.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "contact")
@SequenceGenerator(name = "contact_seq", sequenceName = "contact_seq", allocationSize = 1, initialValue = 1)
@AttributeOverride(name = "id", column = @Column(name = "contact_id"))
public class Contact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "contact_type")
    private String contactType;

    @Column(name = "contact_details")
    private String contactDetails;

    @Column(name = "preference")
    private String preference;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;


}
