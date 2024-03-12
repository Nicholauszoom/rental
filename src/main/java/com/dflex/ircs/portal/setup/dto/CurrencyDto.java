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
public class CurrencyDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
   private String currencyUid;


    private String currencyCode;

    private String currencyName;

    private Long recordStatusId ;



    public CurrencyDto(Long id, UUID currencyUid, String currencyCode, String currencyName, Long recordStatusId) {
        this.id = id;
        this.currencyUid = String.valueOf(currencyUid);
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.recordStatusId = recordStatusId;
    }

    public CurrencyDto(Long id, String currencyUid, String currencyCode, String currencyName, Long recordStatusId) {
        this.id = id;
        this.currencyUid = currencyUid;
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.recordStatusId = recordStatusId;
    }

}

