package pl.surf.web.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name="Roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    @NotBlank(message = "Type of can't be empty")
    private String type;

    public Long getRoleId() {
        return roleId;
    }

    @Override
    public String toString() {
        return type;
    }

    public Role() {
    }

    public Role(String type) {
        this.type = type;
    }

    public Role setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getType() {
        return type;
    }

    public Role setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(roleId, role.roleId) &&
                Objects.equals(type, role.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, type);
    }
}
