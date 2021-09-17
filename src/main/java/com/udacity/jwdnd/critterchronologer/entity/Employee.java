package com.udacity.jwdnd.critterchronologer.entity;

import com.udacity.jwdnd.critterchronologer.user.EmployeeSkill;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

/**
 * This is Entity class for User type Employee and maps Employee table.
 */
@Entity
@Getter @Setter @NoArgsConstructor // Lombok Auto Creation of Getters Setter and default constructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(nullable = false)
    private String name;

    @ElementCollection
    private Set<EmployeeSkill> skills;

    @ElementCollection
    private Set<DayOfWeek> daysAvailable;
}
