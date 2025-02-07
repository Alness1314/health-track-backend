package com.alness.health.common.enums;

public enum AllowedProfiles {
    SADMIN("Master", 0),
    ADMIN("Administrador", 1), 
    EMPLOYEE("Empleado", 2), 
    USER("Paciente", 3);


    private String name;
    private Integer priority;

    AllowedProfiles(String name, Integer priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public Integer getPriority() {
        return priority;
    }
}
