package com.dflex.ircs.portal.payment.api.dto;

import jakarta.xml.bind.annotation.XmlElement;

public class PaymentHeaderDTO {

    private String ackid;
    private String requestid;
    private int dtlcount;
    private String paymentnumber;

    public String getAckid() {
        return ackid;
    }

    @XmlElement(name = "ackid")
    public void setAckid(String ackid) {
        this.ackid = ackid;
    }

    public String getRequestid() {
        return requestid;
    }

    @XmlElement(name = "requestid")
    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public int getDtlcount() {
        return dtlcount;
    }

    @XmlElement(name = "dtlcount")
    public void setDtlcount(int dtlcount) {
        this.dtlcount = dtlcount;
    }

    public String getPaymentnumber() {
        return paymentnumber;
    }

    @XmlElement(name = "paymentnumber")
    public void setPaymentnumber(String paymentnumber) {
        this.paymentnumber = paymentnumber;
    }
}
