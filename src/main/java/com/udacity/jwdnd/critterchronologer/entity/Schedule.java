package com.udacity.jwdnd.critterchronologer.entity;

import com.udacity.jwdnd.critterchronologer.user.EmployeeSkill;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * This is Entity class to set Schedule for Employees and Pets and maps Schedule table.
 */
@Entity
@Getter @Setter @NoArgsConstructor // Lombok Auto Creation of Getters Setter and default constructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(targetEntity = Employee.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Employee> employees;

    @ManyToMany(targetEntity = Pet.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pet> pets;

    @ElementCollection
    private Set<EmployeeSkill> activities;

    private LocalDate date;
}
