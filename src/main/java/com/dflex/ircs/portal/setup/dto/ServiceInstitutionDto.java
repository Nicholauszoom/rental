package com.dflex.ircs.portal.setup.dto;

import com.dflex.ircs.portal.setup.entity.ServiceInstitutionCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ServiceInstitutionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String institutionCode;
    private String institutionNumber;
    private String institutionName;
    private String postalAddress;
    private String physicalAddress;
    private String primaryPhoneNumber;
    private String secondaryPhoneNumber;
    private String email;
    private ServiceInstitutionCategory serviceInstitutionCategoryId;
    private Long recordStatusId;

    public ServiceInstitutionDto(Long id, String s, String institutionCode, String physicalAddress, String postalAddress, String email, String institutionNumber, String institutionName,
                                 String primaryPhoneNumber, String secondaryPhoneNumber, Long recordStatusId,
                                 ServiceInstitutionCategory serviceInstitutionCategory) {

        this.id = id;
        this.institutionCode = institutionCode;
        this.institutionNumber = institutionNumber;
        this.institutionName = institutionName;
        this.postalAddress = postalAddress;
        this.physicalAddress = physicalAddress;
        this.primaryPhoneNumber = primaryPhoneNumber;
        this.secondaryPhoneNumber = secondaryPhoneNumber;
        this.email = email;
        this.serviceInstitutionCategoryId = serviceInstitutionCategory;
        this.recordStatusId = recordStatusId;
    }
}
