package com.alness.health.employee.specification;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.alness.health.employee.entity.EmployeeEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class EmployeeSpecification implements Specification<EmployeeEntity>{

    @SuppressWarnings("null")
    @Override
    @Nullable
    public Predicate toPredicate(Root<EmployeeEntity> root, @Nullable CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {
       return null;
    }

    public Specification<EmployeeEntity> getSpecificationByFilters(Map<String, String> parameters) {
        Specification<EmployeeEntity> specification = Specification.where(this.filterByErased("false"));
        for (Entry<String, String> entry : parameters.entrySet()) {
            switch (entry.getKey()) {
                case "id":
                    specification = specification.and(this.filterById(entry.getValue()));
                    break;
                case "rfc":
                    specification = specification.and(this.filterByName(entry.getValue()));
                    break;
                default:
                    break;
            }
        }
        return specification;
    }

    private Specification<EmployeeEntity> filterById(String id) {
        return (root, query, cb) -> cb.equal(root.<UUID>get("id"), UUID.fromString(id));
    }

    private Specification<EmployeeEntity> filterByName(String rfc) {
        return (root, query, cb) -> cb.equal(root.<String>get("rfc"), rfc);

    }

    private Specification<EmployeeEntity> filterByErased(String erase){
        return (root, query, cb) -> cb.equal(root.<Boolean>get("erased"), Boolean.parseBoolean(erase));
    }
    
}
