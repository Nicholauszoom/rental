package com.dflex.ircs.portal.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StatusDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private  Long id;
    private String statusUid;
    private String statusName;

}
