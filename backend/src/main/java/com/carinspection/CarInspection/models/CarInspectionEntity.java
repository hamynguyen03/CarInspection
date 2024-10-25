package com.carinspection.CarInspection.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class CarInspectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carInspectionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id", nullable = false)
    @JsonIgnoreProperties({"inspections"})
    private CarEntity car;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inspection_criteria_id", nullable = false)
    @JsonIgnoreProperties({"inspections"})
    private InspectionCriteriaEntity inspectionCriteria;

    @Column(nullable = false)
    private boolean isGood;

    @Column(length = 256)
    private String note;

}
