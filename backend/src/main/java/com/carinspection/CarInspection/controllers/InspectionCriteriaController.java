package com.carinspection.CarInspection.controllers;

import com.carinspection.CarInspection.models.InspectionCriteriaEntity;
import com.carinspection.CarInspection.services.InspectionCriteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/inspection-criteria")
public class InspectionCriteriaController {

    private final InspectionCriteriaService inspectionCriteriaService;

    @Autowired
    public InspectionCriteriaController(InspectionCriteriaService inspectionCriteriaService) {
        this.inspectionCriteriaService = inspectionCriteriaService;
    }

    @GetMapping
    public ResponseEntity<List<InspectionCriteriaEntity>> getAllInspectionCriteria() {
        List<InspectionCriteriaEntity> criteriaList = inspectionCriteriaService.getAllInspectionCriteria();
        return ResponseEntity.ok(criteriaList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InspectionCriteriaEntity> getInspectionCriteriaById(@PathVariable("id") Long id) {
        Optional<InspectionCriteriaEntity> criteria = inspectionCriteriaService.getInspectionCriteriaById(id);
        return criteria.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InspectionCriteriaEntity> createInspectionCriteria(@RequestBody InspectionCriteriaEntity inspectionCriteria) {
        InspectionCriteriaEntity savedCriteria = inspectionCriteriaService.saveOrUpdateInspectionCriteria(inspectionCriteria);
        return ResponseEntity.ok(savedCriteria);
    }

    @PutMapping("/{id}/description")
    public ResponseEntity<InspectionCriteriaEntity> updateInspectionCriteriaDescription(
            @PathVariable("id") Long id,
            @RequestBody Map<String, String> requestBody) {
        try {
            String description = requestBody.get("description");
            if (description == null || description.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }

            InspectionCriteriaEntity updatedCriteria = inspectionCriteriaService.updateInspectionCriteriaDescription(id, description);
            return ResponseEntity.ok(updatedCriteria);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInspectionCriteria(@PathVariable("id") Long id) {
        try {
            inspectionCriteriaService.deleteInspectionCriteria(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
