package ru.anscar.nikbabinov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatisticaDTO {
    private String name;
    private String email;
    private int level;
    private int countSolutionTask;
    private int solutionTaskFirstTime;
}
