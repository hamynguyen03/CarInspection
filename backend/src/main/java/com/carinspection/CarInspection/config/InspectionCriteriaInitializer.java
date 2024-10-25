package com.carinspection.CarInspection.config;

import com.carinspection.CarInspection.models.InspectionCriteriaEntity;
import com.carinspection.CarInspection.repositories.InspectionCriteriaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InspectionCriteriaInitializer {

    private final InspectionCriteriaRepository inspectionCriteriaRepository;

    @Autowired
    public InspectionCriteriaInitializer(InspectionCriteriaRepository inspectionCriteriaRepository) {
        this.inspectionCriteriaRepository = inspectionCriteriaRepository;
    }

    @PostConstruct
    public void initialize() {
        if (inspectionCriteriaRepository.count() == 0) {
            // Add hardcoded criteria
            inspectionCriteriaRepository.save(new InspectionCriteriaEntity("Engine Performance"));
            inspectionCriteriaRepository.save(new InspectionCriteriaEntity("Brake Condition"));
            inspectionCriteriaRepository.save(new InspectionCriteriaEntity("Tire Condition"));
            inspectionCriteriaRepository.save(new InspectionCriteriaEntity("Headlights & Signals"));
            inspectionCriteriaRepository.save(new InspectionCriteriaEntity("Windshields & Wipers"));
        }
    }
}

