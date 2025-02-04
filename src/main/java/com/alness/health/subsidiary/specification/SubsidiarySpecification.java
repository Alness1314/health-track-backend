package com.alness.health.subsidiary.specification;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.alness.health.subsidiary.entity.SubsidiaryEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class SubsidiarySpecification implements Specification<SubsidiaryEntity>{

    @Override
    public Predicate toPredicate(Root<SubsidiaryEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }

    public Specification<SubsidiaryEntity> getSpecificationByFilters(Map<String, String> parameters) {
        Specification<SubsidiaryEntity> specification = Specification.where(this.filterByErased("false"));
        for (Entry<String, String> entry : parameters.entrySet()) {
            switch (entry.getKey()) {
                case "id":
                    specification = specification.and(this.filterById(entry.getValue()));
                    break;
                case "nickname":
                    specification = specification.and(this.filterByName(entry.getValue()));
                    break;
                default:
                    break;
            }
        }
        return specification;
    }

    private Specification<SubsidiaryEntity> filterById(String id) {
        return (root, query, cb) -> cb.equal(root.<UUID>get("id"), UUID.fromString(id));
    }

    private Specification<SubsidiaryEntity> filterByName(String nickname) {
        return (root, query, cb) -> cb.equal(root.<String>get("nickname"), nickname);

    }

    private Specification<SubsidiaryEntity> filterByErased(String erase) {
        return (root, query, cb) -> cb.equal(root.<Boolean>get("erased"), Boolean.parseBoolean(erase));
    }
    
}
