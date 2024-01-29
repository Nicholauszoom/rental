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

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "resouce_revenue")
public class ResourceRevenue extends CommonEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false, precision = 11)
    private Long id;

    @Column(name = "id", nullable = false)
    private String revenueCode;

    @Column(name = "revenue_desc", nullable = false)
    private String revenueDesc;

    @Column(name = "unity_code", nullable = false)
    private String unityCode;

    @Column(name = "gl_code", nullable = false)
    private String GLCode;

    @Column(name = "institution_code", nullable = false)
    private String institutionCode;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;
}
