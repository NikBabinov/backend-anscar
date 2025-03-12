package ru.anscar.nikbabinov.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import ru.anscar.nikbabinov.constants.RoleValue;

@Entity
@Getter @Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;

    public Roles(RoleValue roleValue) {
        this.role = roleValue.getValue();
    }
}
