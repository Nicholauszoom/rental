package com.dflex.ircs.portal.payment.api.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.dflex.ircs.portal.invoice.entity.Invoice;
import com.dflex.ircs.portal.invoice.entity.InvoiceItem;
import com.dflex.ircs.portal.invoice.entity.PaidInvoice;
import com.dflex.ircs.portal.invoice.entity.PaidInvoiceItem;
import com.dflex.ircs.portal.invoice.service.InvoiceItemService;
import com.dflex.ircs.portal.invoice.service.PaidInvoiceItemService;
import com.dflex.ircs.portal.invoice.service.PaidInvoiceService;
import com.dflex.ircs.portal.payment.api.dto.PaymentNotificationApiReqBodyDto;
import com.dflex.ircs.portal.payment.api.dto.PaymentNotificationApiReqDetailDto;
import com.dflex.ircs.portal.payment.api.dto.PaymentNotificationApiReqHeaderDto;
import com.dflex.ircs.portal.payment.entity.Payment;
import com.dflex.ircs.portal.payment.service.PaymentService;
import com.dflex.ircs.portal.setup.entity.PaymentFacilitator;
import com.dflex.ircs.portal.setup.service.PaymentFacilitatorService;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.Utils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Consumer to perform post-processes for received invoice payment notifications
 * 
 * @author Augustino Mwageni
 * 
 */

@Component
public class PaymentNotificationConsumer {
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private PaymentFacilitatorService paymentFacilitatorService;
	
	@Autowired
	private InvoiceItemService invoiceItemService;
	
	@Autowired
	private PaidInvoiceService paidInvoiceService;
	
	@Autowired
	private PaidInvoiceItemService paidInvoiceItemService;
	
	@Autowired
	private Utils utils;

	@Value("${rabbitmq.ircs.incoming.retry.exchange}")
	private String inRetryExchange;
	

	@Autowired
	private MessageSource messageSource;

	String message = null;
	String status = null;
	Boolean isError = false;
	Locale currentLocale = LocaleContextHolder.getLocale();
	
	
	@RabbitListener(id = "process_si_payment_notification", queues = { "#{'${rabbitmq.ircs.si.payment.incoming.queues}'.split(',')}" })
	public void processPayment(byte[] data, @Header("amqp_consumerQueue") String queue,
			@Header("amqp_receivedRoutingKey") String routingKey, @Header("retryCounter") final String retryCounter,
			@Header("retryStatusCode") final String retryStatusCode, @Header("receivedTime") final String receivedTime,
			@Header("processRequestId") final String processRequestId, @Header("requestId") final String requestId,
			@Header("apiVersion") final String apiVersion,@Header("clientCode") final String clientCode) {

		Map<String, String> amqHeaders = new ConcurrentHashMap<>();
		amqHeaders.put("retryCounter", retryCounter);
		amqHeaders.put("receivedTime", receivedTime);
		amqHeaders.put("requestId", requestId);
		amqHeaders.put("processRequestId", processRequestId);
		amqHeaders.put("retryStatusCode", retryStatusCode);
		amqHeaders.put("clientCode", clientCode);
		amqHeaders.put("apiVersion", apiVersion);
		

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		PaymentNotificationApiReqBodyDto paymentNotificationApiReqBody = null;
		
		try {

			String messageBody = new String(data, "UTF-8");
			ObjectMapper jsonMapper = new ObjectMapper();
			paymentNotificationApiReqBody = (PaymentNotificationApiReqBodyDto) jsonMapper.readValue(messageBody, PaymentNotificationApiReqBodyDto.class);
			PaymentNotificationApiReqHeaderDto paymentNotificationApiReqHeader = paymentNotificationApiReqBody.getPaymentHeader();
			List<PaymentNotificationApiReqDetailDto> paymentNotificationApiReqDetailList = paymentNotificationApiReqBody.getPaymentDetail().getPaymentDetailList();
			
			Boolean isReconciled = false;
			String remark = null;
			
			for(PaymentNotificationApiReqDetailDto paymentDetail : paymentNotificationApiReqDetailList) {
				
				Optional<PaymentFacilitator> pf = paymentFacilitatorService.findById(paymentDetail.getPaymentFacilitatorId());

				Payment payment = new Payment(
						paymentDetail.getInvoice().getId(),
						paymentDetail.getInvoice().getInvoiceNumber(),
						paymentDetail.getInvoicePaymentNumber(),
						paymentNotificationApiReqHeader.getPaymentNumber(),
						paymentDetail.getTransactionNumber(),
						paymentDetail.getTransactionReference(),
						paymentDetail.getPayerName(),
						paymentDetail.getPayerPhoneNumber(),
						paymentDetail.getPayerEmail(),
						Constants.TRANSACTION_TYPE_CODE_CREDIT,
						java.util.Date.from(LocalDateTime.parse(paymentDetail.getPaymentDate(), dateTimeFormatter)
								.atZone(ZoneId.systemDefault()).toInstant()),
						paymentDetail.getPaymentAmount(),
						paymentDetail.getCurrency(),
						paymentDetail.getPaymentMethod(),
						paymentDetail.getPaymentMethodReference(),
						isReconciled,
						pf.get().getPaymentFacilitatorCode(),
						pf.get(),
						paymentDetail.getCollectionAccount(),
						paymentDetail.getCollectionAccountId(),
						remark,
						paymentNotificationApiReqHeader.getDetailCount(),
						requestId,
						java.util.Date.from(LocalDateTime.parse(receivedTime, dateTimeFormatter)
								.atZone(ZoneId.systemDefault()).toInstant()),
						java.util.Date.from(LocalDateTime.parse(receivedTime, dateTimeFormatter)
								.atZone(ZoneId.systemDefault()).toInstant()),
						new Date(),
						Constants.DEFAULT_SYS_USERID,
						Constants.DEFAULT_SYS_USERNAME
						);
				
				Payment newPayment = paymentService.savePayment(payment);
				
				if(newPayment != null) {
					//Save paid invoice
					Invoice currInvoice = paymentDetail.getInvoice();
					PaidInvoice oldPaidInvoice = paidInvoiceService.findByInvoiceNumberAndServiceInstitutionId(
							currInvoice.getInvoiceNumber(),currInvoice.getServiceInstitution().getId());
					if(oldPaidInvoice != null) {
						
						//Update
						oldPaidInvoice.setIsInvoicePaid(currInvoice.getIsInvoicePaid());
						oldPaidInvoice.setPaidAmount(currInvoice.getPaidAmount());
						oldPaidInvoice.setUpdatedBy(currInvoice.getUpdatedBy());
						oldPaidInvoice.setUpdatedByUserName(currInvoice.getUpdatedByUserName());
						oldPaidInvoice.setUpdatedDate(currInvoice.getUpdatedDate());
						paidInvoiceService.savePaidInvoice(oldPaidInvoice);
						
					} else {
						//Copy		
						
						PaidInvoice paidInvoice = new PaidInvoice(currInvoice.getCreatedBy(), currInvoice.getCreatedByUserName(),currInvoice.getInvoiceUid(),
								currInvoice.getId(),currInvoice.getInvoiceNumber(),currInvoice.getInvoiceTypeId(), currInvoice.getInvoiceDescription(),
								currInvoice.getApplicationNumber(),currInvoice.getPaymentNumber(),currInvoice.getInvoicePaymentNumber(),currInvoice.getReference(),
								currInvoice.getCustomerName(), currInvoice.getCustomerIdentity(),currInvoice.getIdentityTypeId(),
								currInvoice.getCustomerPhoneNumber(), currInvoice.getCustomerEmail(), currInvoice.getCustomerAccount(),
								currInvoice.getInvoiceIssueDate(),currInvoice.getInvoiceExpiryDate(), currInvoice.getIssuedBy(),
								currInvoice.getApprovedBy(),currInvoice.getPaymentOptionId(),currInvoice.getPaymentOptionName(),
								currInvoice.getInvoiceAmount(), currInvoice.getPaidAmount(), currInvoice.getMinimumPaymentAmount(),
								currInvoice.getCurrencyCode(),currInvoice.getExchangeRateValue(), currInvoice.getIsInvoicePaid(),
								currInvoice.getInvoicePayPlanId(),currInvoice.getInvoicePayPlanName(),currInvoice.getNextReminder(),
								currInvoice.getReminderStatusId(), currInvoice.getDetailCount(),currInvoice.getServiceInstitutionCode(),
								currInvoice.getServiceInstitution(), currInvoice.getWorkStationCode(), currInvoice.getWorkStation(),
								currInvoice.getClientCode(), currInvoice.getRequestIdentity(), currInvoice.getReceivedDate(),
								currInvoice.getProcessStartdate(), currInvoice.getProcessEndDate());
						PaidInvoice newPaidInvoice = paidInvoiceService.savePaidInvoice(paidInvoice);
						if(newPaidInvoice != null) {
							
							List<InvoiceItem> invoiceItems = invoiceItemService.findByInvoiceId(currInvoice.getId());
							if(invoiceItems != null && !invoiceItems.isEmpty()) {
								
								List<PaidInvoiceItem> paidInvoiceItems = new ArrayList<>();
								for(InvoiceItem item : invoiceItems) {
									
									paidInvoiceItems.add(new PaidInvoiceItem(item.getInvoiceItemUid(),item.getId(),item.getServiceDepartmentCode(),
											item.getServiceTypeCode(),item.getServiceTypeName(),item.getRevenueSource(), item.getServiceReference(),
											item.getServiceAmount(),newPaidInvoice.getId(),item.getPaymentPriority()));
								}
								
								paidInvoiceItemService.savePaidInvoiceItems(paidInvoiceItems);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			amqHeaders.put("retryStatusCode", messageSource.getMessage("code.1004", null, currentLocale));
			routingKey = routingKey.contains(".retry") ? routingKey : routingKey + ".retry";
			this.utils.publishMsgToRetryExchange(this.inRetryExchange, routingKey, paymentNotificationApiReqBody, amqHeaders);
		}
	}

}
