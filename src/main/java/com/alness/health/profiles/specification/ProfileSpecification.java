package com.alness.health.profiles.specification;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.alness.health.profiles.entity.ProfileEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ProfileSpecification implements Specification<ProfileEntity>{

    @SuppressWarnings("null")
    @Override
    public Predicate toPredicate(Root<ProfileEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }

    public Specification<ProfileEntity> getSpecificationByFilters(Map<String, String> params) {
        Specification<ProfileEntity> specification = Specification.where(this.filterByErased("false"));
        for (Entry<String, String> entry : params.entrySet()) {
            switch (entry.getKey()) {
                case "id":
                    specification = specification.and(this.filterById(entry.getValue()));
                    break;
                case "name":
                    specification = specification.and(this.filterByName(entry.getValue()));
                    break;
                case "erased":
                    specification = specification.and(this.filterByErased(entry.getValue()));
                    break;
                default:
                    break;
            }
        }
        return specification;
    }

    private Specification<ProfileEntity> filterById(String id) {
        return (root, query, cb) -> cb.equal(root.<UUID>get("id"), UUID.fromString(id));
    }

    private Specification<ProfileEntity> filterByName(String name) {
        return (root, query, cb) -> cb.like(root.<String>get("name"), name);

    }

    private Specification<ProfileEntity> filterByErased(String erased) {
        return (root, query, cb) -> cb.equal(root.<Boolean>get("erased"), Boolean.parseBoolean(erased));
    }
    
}
