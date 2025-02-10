package com.alness.health.patients.specification;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.alness.health.patients.entity.PatientsEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class PatientsSpecification implements Specification<PatientsEntity> {

    @SuppressWarnings("null")
    @Override
    @Nullable
    public Predicate toPredicate(Root<PatientsEntity> root, @Nullable CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {
        return null;
    }

    public Specification<PatientsEntity> getSpecificationByFilters(Map<String, String> parameters) {
        Specification<PatientsEntity> specification = Specification.where(this.filterByErased("false"));
        for (Entry<String, String> entry : parameters.entrySet()) {
            switch (entry.getKey()) {
                case "id":
                    specification = specification.and(this.filterById(entry.getValue()));
                    break;
                case "full_name":
                    specification = specification.and(this.filterByFullName(entry.getValue()));
                    break;
                case "rfc":
                    specification = specification.and(this.filterByRfc(entry.getValue()));
                    break;
                case "curp":
                    specification = specification.and(this.filterByCurp(entry.getValue()));
                    break;
                default:
                    break;
            }
        }
        return specification;
    }

    private Specification<PatientsEntity> filterById(String id) {
        return (root, query, cb) -> cb.equal(root.<UUID>get("id"), UUID.fromString(id));
    }

    private Specification<PatientsEntity> filterByFullName(String fullName) {
        return (root, query, cb) -> cb.equal(root.<String>get("fullName"), fullName);

    }

    private Specification<PatientsEntity> filterByCurp(String curp) {
        return (root, query, cb) -> cb.equal(root.<String>get("curp"), curp);

    }

    private Specification<PatientsEntity> filterByRfc(String rfc) {
        return (root, query, cb) -> cb.equal(root.<String>get("rfc"), rfc);

    }

    private Specification<PatientsEntity> filterByErased(String erase) {
        return (root, query, cb) -> cb.equal(root.<Boolean>get("erased"), Boolean.parseBoolean(erase));
    }

}
