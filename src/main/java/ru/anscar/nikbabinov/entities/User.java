package ru.anscar.nikbabinov.entities;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.anscar.nikbabinov.constants.RoleValue;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;
    private Date userCreateDate;
    private Date userUpdateDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "authority_id")
    private Role role;

    @Builder(builderMethodName = "baseBuilder")
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @PrePersist
    protected void onCreate() {
        this.userCreateDate = new Date();
        this.userUpdateDate = new Date();
        if (role == null) {
            role = new Role(RoleValue.READ);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.userUpdateDate = new Date();
    }

    public boolean hasAuthority(String authorityValue) {
        return role != null && role.getAuthority().equals(authorityValue);
    }
}
