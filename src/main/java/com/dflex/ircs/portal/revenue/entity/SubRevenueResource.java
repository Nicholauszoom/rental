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
@Table(name = "sub_resouce_revenue_tbl",indexes = {
@Index(name = "idx_sub_resouce_revenue", columnList = "revenue_resource_id")},
        uniqueConstraints = {
@UniqueConstraint(columnNames = {"revenue_resource_id"})
        })
public class SubRevenueResource extends CommonEntity implements Serializable {

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
    @JoinColumn(name = "revenue_resource_id", nullable = false)
    private RevenueResource revenueResource;
    public void setRevenueResourceId(Long id) {
        this.revenueResource = new RevenueResource();
        this.revenueResource.setId(id);
    }

    public SubRevenueResource(Date createdDate, Date updatedDate, UUID createdBy, String createdByUserName, UUID updatedBy, String updatedByUserName, Long id, String subRevenueCode, String subRevenueDesc, String revenueCode, String paymentOptions, RevenueResource revenueResource) {
        super(createdDate, updatedDate, createdBy, createdByUserName, updatedBy, updatedByUserName);
        this.id = id;
        this.subRevenueCode = subRevenueCode;
        this.subRevenueDesc = subRevenueDesc;
        this.revenueCode = revenueCode;
        this.paymentOptions = paymentOptions;
        this.revenueResource = revenueResource;
    }

}
