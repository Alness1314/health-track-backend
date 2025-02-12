package com.alness.health.inventory.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.alness.health.subsidiary.entity.SubsidiaryEntity;
import com.alness.health.suppliers.entity.SuppliersEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory")
public class InventoryEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    private String name;
    private String description;
    private String unit;
    private Integer minStock;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "subsidiary_id", nullable = false)
    private SubsidiaryEntity subsidiary;

    @Column(name = "create_at", nullable = false, columnDefinition = "timestamp without time zone")
    private LocalDateTime createAt;

    @Column(name = "update_at", nullable = false, updatable = true, columnDefinition = "timestamp without time zone")
    private LocalDateTime updateAt;

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean erased;
}
