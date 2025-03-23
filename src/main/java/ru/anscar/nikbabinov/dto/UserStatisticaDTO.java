package ru.anscar.nikbabinov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatisticaDTO {
    private Long id;
    private String name;
    private String email;
    private int level;
    private int countSolutionTask;
    private int solutionTaskFirstTime;
    private Date userCreateDate;
    private Date userUpdateDate;
    private String role;
}
