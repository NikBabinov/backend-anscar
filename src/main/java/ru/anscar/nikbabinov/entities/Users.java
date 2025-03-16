package ru.anscar.nikbabinov.entities;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.anscar.nikbabinov.constants.RoleValue;

import java.util.Date;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {

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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Roles roles;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private UsersStatistica usersStatistica;

    @Builder(builderMethodName = "baseBuilder")
    public Users(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @PrePersist
    protected void onCreate() {
        this.userCreateDate = new Date();
        this.userUpdateDate = new Date();
        if (roles == null) {
            roles = new Roles(RoleValue.ROLE_USER);
        }
        if (usersStatistica == null) {
            usersStatistica = new UsersStatistica();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.userUpdateDate = new Date();
    }
}
