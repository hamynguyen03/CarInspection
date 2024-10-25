package com.carinspection.CarInspection.services;

import com.carinspection.CarInspection.models.InspectionCriteriaEntity;
import com.carinspection.CarInspection.repositories.InspectionCriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InspectionCriteriaService {

    private final InspectionCriteriaRepository inspectionCriteriaRepository;

    @Autowired
    public InspectionCriteriaService(InspectionCriteriaRepository inspectionCriteriaRepository) {
        this.inspectionCriteriaRepository = inspectionCriteriaRepository;
    }

    public List<InspectionCriteriaEntity> getAllInspectionCriteria() {
        return inspectionCriteriaRepository.findAll();
    }

    public Optional<InspectionCriteriaEntity> getInspectionCriteriaById(Long criteriaId) {
        return inspectionCriteriaRepository.findById(criteriaId);
    }

    public InspectionCriteriaEntity saveOrUpdateInspectionCriteria(InspectionCriteriaEntity inspectionCriteriaEntity) {
        return inspectionCriteriaRepository.save(inspectionCriteriaEntity);
    }

    public void deleteInspectionCriteria(Long criteriaId) {
        inspectionCriteriaRepository.deleteById(criteriaId);
    }

    public InspectionCriteriaEntity updateInspectionCriteriaDescription(Long criteriaId, String newDescription) {
        String cleanedDescription = cleanDescription(newDescription);

        List<InspectionCriteriaEntity> existingCriteria = inspectionCriteriaRepository.findAll();
        for (InspectionCriteriaEntity criteria : existingCriteria) {
            if (cleanDescription(criteria.getCriteriaDesc()).equalsIgnoreCase(cleanedDescription) && !criteria.getInspectionCriteriaId().equals(criteriaId)) {
                throw new IllegalArgumentException("Description already exists");
            }
        }

        InspectionCriteriaEntity criteria = inspectionCriteriaRepository.findById(criteriaId)
                .orElseThrow(() -> new RuntimeException("Inspection criteria not found with ID: " + criteriaId));

        criteria.setCriteriaDesc(newDescription.trim());
        return inspectionCriteriaRepository.save(criteria);
    }

    private String cleanDescription(String description) {
        return description.toLowerCase().replaceAll("\\s+", "").trim();
    }

}
