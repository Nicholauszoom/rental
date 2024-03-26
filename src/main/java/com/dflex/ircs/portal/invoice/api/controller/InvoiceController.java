package com.dflex.ircs.portal.invoice.api.controller;

import java.io.StringReader;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dflex.ircs.portal.application.entity.ApplicationWorkFlow;
import com.dflex.ircs.portal.application.service.ApplicationWorkFlowService;
import com.dflex.ircs.portal.auth.dto.CommunicationApiDetailsDto;
import com.dflex.ircs.portal.auth.service.ClientSystemHostService;
import com.dflex.ircs.portal.auth.service.CommunicationApiService;
import com.dflex.ircs.portal.data.entity.FormData1;
import com.dflex.ircs.portal.data.service.FormDataService;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceDetailDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceServiceDetailDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiAckBodyDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiAckDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiResBodyDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiResDetailDto;
import com.dflex.ircs.portal.invoice.api.dto.InvoiceSubmissionApiResDto;
import com.dflex.ircs.portal.invoice.entity.Invoice;
import com.dflex.ircs.portal.invoice.entity.InvoiceItem;
import com.dflex.ircs.portal.invoice.service.InvoiceItemService;
import com.dflex.ircs.portal.invoice.service.InvoiceService;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.PKIUtils;
import com.dflex.ircs.portal.util.Response;
import com.dflex.ircs.portal.util.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

/**
 * 
 * @author Augustino Mwageni
 * 
 * Receive Invoice Requests
 *
 */
@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {
	
	@Autowired
	private ApplicationWorkFlowService applicationWorkFlowService;
	
	@Autowired
	private FormDataService formDataService;
	
	@Autowired
	private CommunicationApiService communicationApiService;
	
	@Autowired
	private ClientSystemHostService clientSystemHostService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private InvoiceItemService invoiceItemService;
	
	@Autowired
	private Utils utils;
	
	@Autowired
	private PKIUtils pkiUtils;
	
	@Autowired
	private MessageSource messageSource;
	
	String status = "";
	String message = "";
	Boolean error = false;
	
	@PostMapping("/details/applicationuid/{applicationuid}")
	public ResponseEntity<?> getInvoiceDetailsByAppicationUid(@PathVariable("applicationuid") String applicationUid
			,HttpServletRequest request) {
		
		InvoiceDetailDto invoiceDetail = new InvoiceDetailDto();
		Optional<FormData1> application = formDataService.findFormData1ByApplicationUid(
				UUID.fromString(applicationUid));
		if(application.isPresent()) {
			
			Invoice invoice = invoiceService.findByReferenceAndReferencePath(String.valueOf(application.get().getId()),Constants.DATA_PATH_1);
			if(invoice != null) {
				
				List<InvoiceItem> invoiceItems = invoiceItemService.findByInvoiceIdAndRecordStatusId(invoice.getId(),Constants.RECORD_STATUS_ACTIVE);
				
				invoiceDetail.setServiceInstitutionCode(invoice.getServiceInstitutionCode());
				invoiceDetail.setServiceInstitutionName(invoice.getServiceInstitution().getInstitutionName());
				invoiceDetail.setBillPaymentOption(invoice.getPaymentOptionId());
				invoiceDetail.setBillPaymentOptionName(invoice.getPaymentOptionName());
				invoiceDetail.setCurrencyCode(invoice.getCurrencyCode());
				invoiceDetail.setCustomerEmail(invoice.getCustomerEmail());
				invoiceDetail.setCustomerName(invoice.getCustomerName());
				invoiceDetail.setCustomerPhoneNumber(invoice.getCustomerPhoneNumber());
				invoiceDetail.setInvoiceAmount(invoice.getInvoiceAmount());
				invoiceDetail.setInvoiceMinPayAmount(invoice.getMinimumPaymentAmount());
				invoiceDetail.setInvoiceDescription(invoice.getInvoiceDescription());
				invoiceDetail.setInvoiceExpiryDate(invoice.getInvoiceExpiryDate());
				invoiceDetail.setInvoiceIssuedDate(invoice.getInvoiceIssueDate());
				invoiceDetail.setPaymentNumber(invoice.getPaymentNumber());
				invoiceDetail.setInvoicePaymentNumber(invoice.getInvoicePaymentNumber());
				invoiceDetail.setInvoiceReference(invoice.getReference());
				invoiceDetail.setApplicationNumber(invoice.getApplicationNumber());
				invoiceDetail.setWorkStationCode(invoice.getWorkStationCode());
				invoiceDetail.setWorkStationName(invoice.getWorkStation().getWorkStationName());
				invoiceDetail.setPaymentOptionName(invoice.getPaymentOptionName());
				invoiceDetail.setPaymentOptionId(invoice.getPaymentOptionId());
				
				InvoiceServiceDetailDto serviceDetail = new InvoiceServiceDetailDto();
				serviceDetail.setServiceAmount(invoiceItems.get(0).getServiceAmount());
				serviceDetail.setServiceTypeCode(invoiceItems.get(0).getServiceTypeCode());
				serviceDetail.setServiceTypeName(invoiceItems.get(0).getServiceTypeName());
				serviceDetail.setTotalUnits(1);
				List<InvoiceServiceDetailDto> serviceDetails = new ArrayList<>();
				serviceDetails.add(serviceDetail);
				
				invoiceDetail.setServiceDetails(serviceDetails);
			}
		}
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,invoiceDetail,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
		
}
