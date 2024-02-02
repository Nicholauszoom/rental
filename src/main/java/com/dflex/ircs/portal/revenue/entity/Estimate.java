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
@Table(name = "estimate")
public class Estimate  extends CommonEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false, precision = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sub_revenue_code")
    private String subRevenueCode;

    @Column(name = "financial_year")
    private Date financialYear;

    @Column(name = "estimated_collection")
    private String estimatedCollection;

    @Column(name = "actual_collection")
    private String actualCollection;

    @OneToOne
    @JoinColumn(name = "sub_revenue_resource_id", nullable = false)
    @MapsId
    private SubRevenueResource subrevenueResource;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Date updatedAt;
}
