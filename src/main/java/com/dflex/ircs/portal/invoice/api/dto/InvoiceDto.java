package com.dflex.ircs.portal.invoice.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class InvoiceDto implements Serializable {
  
	private static final long serialVersionUID = 1L;
  
  private String invoiceReference;
  
  @NotEmpty(message = "Invalid customer name. It cannot be empty.")
  @Size(max = 200, message = "Customer name can not exceed {max} characters long")
  private String customerName;
  
  @NotEmpty(message = "Invalid billed phone number. It cannot be empty.")
  @Size(min = 10, max = 12, message = "Billed phone number can have minimum of {min} and maximum of {max} characters long")
  private String customerPhoneNumber;
  
  @Email(message = "Invalid email")
  private String customerEmail;
  
  @NotEmpty(message = "Invalid invoice description. It cannot be empty.")
  @Size(max = 500, message = "Invoice description can not exceed {max} characters long")
  private String invoiceDescription;
  
  @DecimalMin(value = "0.0", inclusive = false, message = "Invalid invoice amount. Invoice amount cannot be empty, zero or less than zero")
  @Digits(integer = 32, fraction = 2)
  private BigDecimal invoiceAmount;
  
  @DecimalMin(value = "0.0", inclusive = false, message = "Invalid invoice maximum payable amount. Maximum payable amount cannot be empty, zero or less than zero")
  @Digits(integer = 32, fraction = 2)
  private BigDecimal invoiceMaxAmount;
  
  @DecimalMin(value = "0.0", inclusive = false, message = "Invalid invoice minimum payable amount. Minimum payable amount cannot be empty, zero or less than zero")
  @Digits(integer = 32, fraction = 2)
  private BigDecimal invoiceMinAmount;
  
  @NotEmpty(message = "Invalid bill currency. Select currency from the list provided")
  private String currencyCode;
  
  @Future(message = "Invalid bill expiry date. Bill expiry date cannot be empty or include invalid date characters.")
  private Date invoiceExpiryDate;
  
  private String invoiceExpiryDateHdn;
  
  @NotNull(message = "Select bill payment type from the list.")
  private Long billPaymentType;
  
  @NotNull(message = "Specify service charged from the list.")
  private Long serviceType;
  
}
