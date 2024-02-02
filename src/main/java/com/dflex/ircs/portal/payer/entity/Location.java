package com.dflex.ircs.portal.payer.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "location")
@SequenceGenerator(name = "location_seq", sequenceName = "location_seq", allocationSize = 1, initialValue = 1)
@AttributeOverride(name = "id", column = @Column(name = "location_id"))
public class Location implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "location_type")
    private String locationType;

    @Column(name = "location_details")
    private String locationDetails;

    @Column(name = "lat_code")
    private Float latCode;

    @Column(name = "long_code")
    private Float longCode;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

}
