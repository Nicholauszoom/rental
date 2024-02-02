package com.dflex.ircs.portal.payer.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data

@Table(name = "institution",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"location_id", "contact_id"})
        })
public class Institution implements Serializable {

    @Id
    private  Long id;

    @Column(name = "institution_name")
    private String institutionName;

    @Column(name = "institution_code")
    private Float institutionCode;

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
