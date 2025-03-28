package com.alness.health.users.specification;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.alness.health.users.entity.UserEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class UserSpecification implements Specification<UserEntity>{

    @SuppressWarnings("null")
    @Override
    @Nullable
    public Predicate toPredicate(Root<UserEntity> root, @Nullable CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {
        return null;
    }

    public Specification<UserEntity> getSpecificationByFilters(Map<String, String> params){
        Specification<UserEntity> specification = Specification.where(this.filterByErased("false"));
        for (Entry<String, String> entry : params.entrySet()) {
            switch (entry.getKey()) {
                case "id":
                    specification = specification.and(this.filterById(entry.getValue()));
                    break;
                case "username":
                    specification = specification.and(this.filterByUsername(entry.getValue()));
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

    private Specification<UserEntity> filterById(String id) {
        return (root, query, cb) -> cb.equal(root.<UUID>get("id"), UUID.fromString(id));
    }

    private Specification<UserEntity> filterByUsername(String username) {
        return (root, query, cb) -> cb.like(root.<String>get("username"), username);
        
    }

    private Specification<UserEntity> filterByErased(String enabled) {
        return (root, query, cb) -> cb.equal(root.<Boolean>get("erased"), Boolean.parseBoolean(enabled));
    }
    
}
