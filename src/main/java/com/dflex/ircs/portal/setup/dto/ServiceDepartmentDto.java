package com.dflex.ircs.portal.setup.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ServiceDepartmentDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String serviceDepartmentUid;
    private String departmentCode;
    private String departmentName;
    private Long serviceInstitutionId;
    private Long recordStatusId ;
}
