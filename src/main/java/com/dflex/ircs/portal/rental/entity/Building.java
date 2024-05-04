package com.dflex.ircs.portal.rental.entity;

import com.dflex.ircs.portal.setup.entity.ServiceInstitution;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_building")
public class Building  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "building_id_generator")
    @SequenceGenerator(name = "building_id_generator",
            sequenceName = "seq_building", initialValue = 1, allocationSize = 1)
    @Column(name = "id", nullable = false, precision = 11)
    private Long id;

    @GeneratedValue
    @UuidGenerator
    @Column(name="building_uid",nullable = false)
    private UUID buildingUid;

    @PrePersist
    protected void onCreate() {

        setBuildingUid(java.util.UUID.randomUUID());
    }

    @Column(name = "property_number", length = 64, nullable = false)
    private String propertyNumber;

    @Column(name = "property_name", length = 100, nullable = false)
    private String propertyName;

    @Column(name = "block_number", length = 64, nullable = false)
    private String blockNumber;

    @Column(name = "plot_number", length = 64, nullable = false)
    private String plotNumber;

    @Column(name = "location", length = 100, nullable = false)
    private String location;

    @Column(name = "property_region", length = 100, nullable = false)
    private String propertyRegion;

    @Column(name = "property_district", length = 100, nullable = false)
    private String propertyDistrict;

    @Column(name = "property_area", length = 100, nullable = false)
    private String propertyArea;

    @Column(name = "property_size", length = 64, nullable = false)
    private double propertySize;


}
