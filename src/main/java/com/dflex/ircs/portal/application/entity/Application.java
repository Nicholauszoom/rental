package com.dflex.ircs.portal.application.entity;

import com.dflex.ircs.portal.payer.entity.Payer;
import com.dflex.ircs.portal.revenue.entity.SubRevenueResource;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "application",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"sub_revenue_resource_id", "payer_id"})})

public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "application_code")
    private String  applicationCode;

    @Column(name = "application_number")
    private Long applicationNumber;

    @Column(name = "application_date")
    private Date applicationDate;

    @Column(name = "application_status")
    private String applicationStatus;

    @Column(name = "sub_verenue_code")
    private String subRevenueCode;

     @Column(name = "payer_code")
    private String payerCode;

    @ManyToOne
    @JoinColumn(name = "sub_revenue_resource_id")
    private SubRevenueResource subRevenueResource;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private Payer payer;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

}
