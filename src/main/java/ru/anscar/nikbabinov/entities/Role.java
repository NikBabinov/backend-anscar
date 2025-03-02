package ru.anscar.nikbabinov.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import ru.anscar.nikbabinov.constants.RoleValue;

@Entity
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;

    public Role(RoleValue roleValue) {
        this.authority = roleValue.getValue();
    }
}
