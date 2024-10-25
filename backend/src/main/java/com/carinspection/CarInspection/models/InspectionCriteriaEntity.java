package com.carinspection.CarInspection.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class InspectionCriteriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inspectionCriteriaId;

    @Column(nullable = false, length = 128)
    private String criteriaDesc;

    @OneToMany(mappedBy = "inspectionCriteria", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"inspectionCriteria"})
    private List<CarInspectionEntity> inspections;

    public InspectionCriteriaEntity(String criteriaDesc) {
        this.criteriaDesc = criteriaDesc;
    }
}
