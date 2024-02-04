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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_resource_revenue")
public class RevenueResource  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "revenue_code", nullable = false)
    private String revenueCode;

    @Column(name = "revenue_desc", nullable = false)
    private String revenueDesc;

    @Column(name = "unity_code", nullable = false)
    private String unityCode;

    @Column(name = "gl_code", nullable = false)
    private String glCode;

    @Column(name = "institution_code", nullable = false)
    private String institutionCode;


    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

}
