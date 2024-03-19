package com.dflex.ircs.portal.setup.controller;

import com.dflex.ircs.portal.setup.dto.ServiceDepartmentDto;
import com.dflex.ircs.portal.setup.dto.ServiceInstitutionDto;
import com.dflex.ircs.portal.setup.entity.ServiceDepartment;
import com.dflex.ircs.portal.setup.entity.ServiceInstitution;
import com.dflex.ircs.portal.setup.service.CurrencyService;
import com.dflex.ircs.portal.setup.service.ServiceDepartmentService;
import com.dflex.ircs.portal.util.Response;
import com.dflex.ircs.portal.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


@RestController
@RequestMapping("/api/serviceDepartment")
public class ServiceDepartmentController {


    @Autowired
    private ServiceDepartmentService serviceDepartmentService;

    @Autowired
    private Utils utils;

    @Autowired
    private MessageSource messageSource;

    Locale currentLocale = LocaleContextHolder.getLocale();

    String status = "";
    String message = "";
    Boolean error = false;

    protected Logger logger = LoggerFactory.getLogger(ServiceDepartmentController.class);

    /**
     * Create   Currency
     * @body currencyDto
     * @return ResponseEntity
     */

    /**
     * List  Service Department
     * @param request
     * @return ResponseEntity
     */

    @PostMapping("/list")
    public ResponseEntity<?> getServiceTypes(HttpServletRequest request) {
        List<ServiceDepartmentDto> departmentDtoList = new ArrayList<>();
        try {
            List<ServiceDepartment> serviceData = serviceDepartmentService.findAll();
            if(serviceData != null){
                for (ServiceDepartment service : serviceData) {
                    ServiceDepartmentDto serviceDepartmentDto = new ServiceDepartmentDto();
                    serviceDepartmentDto.setId(service.getId());
                    serviceDepartmentDto.setDepartmentCode(service.getDepartmentCode());
                    serviceDepartmentDto.setServiceDepartmentUid(String.valueOf(service.getServiceDepartmentUid()));
                    serviceDepartmentDto.setDepartmentName(service.getDepartmentName());
                    serviceDepartmentDto.setServiceInstitutionId(service.getServiceInstitution().getId());
                    serviceDepartmentDto.setRecordStatusId(service.getRecordStatusId());

                    departmentDtoList.add(serviceDepartmentDto);

                }
                message = messageSource.getMessage("message.1001", null, currentLocale);
                status = messageSource.getMessage("code.1001", null, currentLocale);
                error = false;
            } else {
                message = messageSource.getMessage("message.1007", null, currentLocale);
                status = messageSource.getMessage("code.1007", null, currentLocale);
                error = true;
            }
            Response response = new Response(String.valueOf(Calendar.getInstance().getTime()), status, error, message, departmentDtoList, request.getRequestURI());
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            message = messageSource.getMessage("message.1004",null, currentLocale);
            status = messageSource.getMessage("code.1004",null, currentLocale);
            error  = true;
            Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,error,message,null,request.getRequestURI());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}
