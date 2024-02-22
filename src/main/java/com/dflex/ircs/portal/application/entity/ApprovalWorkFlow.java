/**package com.dflex.ircs.portal.application.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "approval_work_flow", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"application_id"})})

public class ApprovalWorkFlow implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "work_flow_assign_id")
    private String workFlowAssignId;

    @Column(name = "action_date")
    private Date actionDate;

    @Column(name = "action_status")
    private String actionStatus;

    @Column(name = "app_number")
    private Long appNumber;

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
