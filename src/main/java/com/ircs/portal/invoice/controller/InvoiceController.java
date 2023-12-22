package com.ircs.portal.invoice.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;
//import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ircs.portal.invoice.dto.InvoiceDto;
import com.ircs.portal.setup.service.CurrencyService;
import com.ircs.portal.setup.service.ServiceTypeSourceService;
import com.ircs.portal.util.Constants;

@Controller
@RequestMapping({"/invoice"})
public class InvoiceController {

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private ServiceTypeSourceService serviceTypeSourceService;

	@Autowired
	private MessageSource messageSource;
  
	private ObjectMapper mapper = null;
  
	Locale currentLocale = LocaleContextHolder.getLocale();

	String message = "";

	@GetMapping({ "", "/create" })
	public String getInvoiceCreate(Model model, InvoiceDto invoice) {
		model.addAttribute("invoice", invoice);
		Long financialYearId = 2024L;
		Long serviceDepartmentId = 1L;
		getDefaultData(model, financialYearId, serviceDepartmentId);
		return "invoice/invoice_create";
	}

	private void getDefaultData(Model model, Long financialYearId, Long serviceDepartmentId) {
		Map<Long, String> currencies = currencyService.findByRecordStatusId(Constants.RECORD_STATUS_ACTIVE);
		List<Map<String, String>> serviceTypeSources = serviceTypeSourceService
				.findServiceTypesByServiceDepartmentIdAndFinancialYearIdAndRecordStatusId(serviceDepartmentId, financialYearId,
						Constants.RECORD_STATUS_ACTIVE);
		model.addAttribute("currencies", currencies);
		model.addAttribute("serviceTypeSources", serviceTypeSources);
	}
 
}
