/**package com.dflex.ircs.portal.application.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "approval_workflow_item",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"approval_workflow_id"})})
public class ApprovalWorkFlowItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "action")
    private String action;

    @ManyToOne
    @JoinColumn (name = "approval_workflow_id")
    private ApprovalWorkFlow approvalWorkFlow;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

}**/
