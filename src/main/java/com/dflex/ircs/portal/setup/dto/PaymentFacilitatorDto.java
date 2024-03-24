package com.dflex.ircs.portal.setup.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PaymentFacilitatorDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String paymentFacilitatorCode;

    private String paymentFacilitatorName;

    private String paymentFacilitatorShortName;

    private Long recordStatusId;



    public PaymentFacilitatorDto(Long id, String s, String paymentFacilitatorName, String paymentFacilitatorShortName,
                                 String paymentFacilitatorCode, Long recordStatusId) {
        this.id = id;
        this.paymentFacilitatorCode = paymentFacilitatorCode;
        this.paymentFacilitatorName = paymentFacilitatorName;
        this.paymentFacilitatorShortName = paymentFacilitatorShortName;
        this.recordStatusId = recordStatusId;
    }
}
