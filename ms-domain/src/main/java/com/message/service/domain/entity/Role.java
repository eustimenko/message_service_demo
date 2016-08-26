package com.message.service.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "role", uniqueConstraints = @UniqueConstraint(columnNames = "name", name = "unique_role"))
public class Role extends BaseEntity {

    public static final String ROOT = "ROLE_ROOT";
    public static final String USER = "ROLE_USER";

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return name.equals(role.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
