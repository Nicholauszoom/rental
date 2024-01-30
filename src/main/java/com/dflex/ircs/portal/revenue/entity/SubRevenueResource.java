package com.dflex.ircs.portal.revenue.entity;


import com.dflex.ircs.portal.util.CommonEntity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "sub_resouce_revenue")
public class SubRevenueResource extends CommonEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false, precision = 11)
    private Long id;

    @Column(name = "sub_revenue_code", nullable = false, precision = 11)
    private String subRevenueCode;

    @Column(name = "sub_revenue_desc", nullable = false)
    private String subRevenueDesc;

    @Column(name = "sub_revenue_code", nullable = false)
    private String revenueCode;

    @Column(name = "sub_payment_option", nullable = false)
    private  String paymentOptions;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revenue_resource_id", nullable = false)
    @MapsId
    private RevenueResource revenueResource;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Date updatedAt;


}
