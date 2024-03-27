package com.dflex.ircs.portal.setup.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OtherServiceInstitutionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String otherServiceInstitutionUid;
    private String institutionCode;
    private String institutionName;
    private String postalAddress;
    private String physicalAddress;
    private String primaryPhoneNumber;
    private String secondaryPhoneNumber;
    private String email;
    private Long recordStatusId ;

    public OtherServiceInstitutionDto(Long id, String institutionCode, String physicalAddress,
                                      String postalAddress, String email, UUID otherServiceInstitutionUid,
                                      String institutionName, String primaryPhoneNumber,
                                      String secondaryPhoneNumber, Long recordStatusId) {

        this.id = id;
        this.otherServiceInstitutionUid = String.valueOf(otherServiceInstitutionUid);
        this.institutionCode = institutionCode;
        this.institutionName = institutionName;
        this.postalAddress = postalAddress;
        this.physicalAddress = physicalAddress;
        this.primaryPhoneNumber = primaryPhoneNumber;
        this.secondaryPhoneNumber = secondaryPhoneNumber;
        this.email = email;
        this.recordStatusId = recordStatusId;
    }


}
