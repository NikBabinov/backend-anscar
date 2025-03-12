package ru.anscar.nikbabinov.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersStatistica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "int default 1")
    private int level;
    @Column(columnDefinition = "int default 1")
    private int countSolutionTask;
    @Column(columnDefinition = "int default 1")
    private int solutionTaskFirstTime;

    @OneToOne(mappedBy = "usersStatistica")
    private Users users;
}
