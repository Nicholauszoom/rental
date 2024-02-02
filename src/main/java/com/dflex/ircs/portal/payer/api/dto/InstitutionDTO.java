package com.dflex.ircs.portal.payer.api.dto;

import com.dflex.ircs.portal.payer.entity.Contact;
import com.dflex.ircs.portal.payer.entity.Location;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data

public class InstitutionDTO {
    private String institutionName;

    private Float institutionCode;
    private Contact contact;
    private Location location;
    private Date createdAt;
    private Date updatedAt;
}
