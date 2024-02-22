/**package com.dflex.ircs.portal.application.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "application_details",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"application_id"})})
public class ApplicationDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "app_details_desc")
    private String appDetialsDesc;

    @Column(name = "app_form_number")
    private String appFormNumber;

    @Column(name = "app_form_data_status")
    private String appFormDataStatus;

    @ManyToOne
    @JoinColumn (name = "application_id")
    private Application application;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;


}**/
