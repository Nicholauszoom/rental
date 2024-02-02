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
@Table(name = "tab_resouce_revenue")
public class RevenueResource extends CommonEntity implements Serializable {

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


    public RevenueResource(Date createdDate, Date updatedDate, UUID createdBy, String createdByUserName, UUID updatedBy, String updatedByUserName, Long id, String revenueCode, String revenueDesc, String unityCode, String GLCode, String institutionCode, Date createdAt, Date updatedAt) {
        super(createdDate, updatedDate, createdBy, createdByUserName, updatedBy, updatedByUserName);
        this.id = id;
        this.revenueCode = revenueCode;
        this.revenueDesc = revenueDesc;
        this.unityCode = unityCode;
        this.glCode = GLCode;
        this.institutionCode = institutionCode;

    }
}
