package com.dflex.ircs.portal.invoice.api.dto;

import java.util.Date;

import com.dflex.ircs.portal.setup.entity.ServiceInstitution;
import com.dflex.ircs.portal.setup.entity.WorkStation;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Augustino Mwageni
 *
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class InvoiceTodbDto {
	
	private InvoiceSubmissionApiReqHeaderDto invoiceHeader;
	
	private InvoiceSubmissionApiReqDetailDto invoiceDetail;
	
	private ServiceInstitution serviceInstitution;
	
	private WorkStation workStation;
	
	private String invoicePaymentNumber;
	
	private Date processStartTime;
	
	private Date processEndTime;

	public Long clientId;
	
}
