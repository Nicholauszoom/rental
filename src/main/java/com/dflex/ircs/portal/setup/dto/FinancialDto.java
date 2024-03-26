package com.dflex.ircs.portal.setup.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FinancialDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String financialYearUid;
    private Integer shortYear;
    private Date yearStart;
    private Date yearEnd;
    private Long recordStatusId;
    private Long financialYearId;
}
