package com.dflex.ircs.portal.payment.api.dto;


import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetailDTO {

    private String transactionnumber;
    private String transactionreference;
    private String serviceinstitution;
    private String serviceinstitutionname;
    private String invoicepaymentnumber;
    private double paymentamount;
    private String currency;
    private String paymentdate;
    private String paymentmethod;
    private String payername;
    private String payerphonenumber;
    private String payeremail;

    @XmlElement(name = "transactionnumber")
    public String getTransactionnumber() {return transactionnumber;
    }

    public void setTransactionnumber(String transactionnumber) {this.transactionnumber = transactionnumber;
    }

    @XmlElement(name = "transactionreference")
    public String getTransactionreference() {return transactionreference;
    }

    public void setTransactionreference(String transactionreference) {this.transactionreference = transactionreference;
    }

    @XmlElement(name = "serviceinstitution")
    public String getServiceinstitution() {return serviceinstitution;
    }

    public void setServiceinstitution(String serviceinstitution) {this.serviceinstitution = serviceinstitution;
    }

    @XmlElement(name = "serviceinstitutionname")
    public String getServiceinstitutionname() {return serviceinstitutionname;
    }

    public void setServiceinstitutionname(String serviceinstitutionname) {this.serviceinstitutionname = serviceinstitutionname;
    }

    @XmlElement(name = "invoicepaymentnumber")
    public String getInvoicepaymentnumber() {return invoicepaymentnumber;
    }

    public void setInvoicepaymentnumber(String invoicepaymentnumber) {this.invoicepaymentnumber = invoicepaymentnumber;
    }

    @XmlElement(name = "paymentamount")
    public double getPaymentamount() {return paymentamount;
    }

    public void setPaymentamount(double paymentamount) {this.paymentamount = paymentamount;
    }

    @XmlElement(name = "currency")
    public String getCurrency() {return currency;
    }

    public void setCurrency(String currency) {this.currency = currency;
    }

    @XmlElement(name = "paymentdate")
    public String getPaymentdate() {return paymentdate;
    }

    public void setPaymentdate(String paymentdate) {
        this.paymentdate = paymentdate;
    }

    @XmlElement(name = "paymentmethod")
    public String getPaymentmethod() {return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {this.paymentmethod = paymentmethod;
    }
    @XmlElement(name = "payername")
    public String getPayername() {return payername;
    }

    public void setPayername(String payername) {
        this.payername = payername;
    }
    @XmlElement(name = "payerphonenumber")
    public String getPayerphonenumber() {return payerphonenumber;
    }

    public void setPayerphonenumber(String payerphonenumber) {this.payerphonenumber = payerphonenumber;
    }

    @XmlElement(name = "payeremail")
    public String getPayeremail() {return payeremail;
    }
    public void setPayeremail(String payeremail) {this.payeremail = payeremail;
    }
}
