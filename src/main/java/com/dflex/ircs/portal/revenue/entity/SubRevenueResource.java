package com.dflex.ircs.portal.revenue.entity;


import com.dflex.ircs.portal.util.CommonEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sub_resource_revenue_tbl")
public class SubRevenueResource  implements Serializable {

    @Id
    @Column(name = "id", nullable = false, precision = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sub_revenue_code", nullable = false)
    private String subRevenueCode;

    @Column(name = "sub_revenue_desc", nullable = false)
    private String subRevenueDesc;

    @Column(name = "revenue_code", nullable = false)
    private String revenueCode;

    @Column(name = "sub_payment_option", nullable = false)
    private  String paymentOptions;

    @ManyToOne
    @JoinColumn(name = "revenue_resource_id", nullable = true)
    private RevenueResource revenueResource;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

}
