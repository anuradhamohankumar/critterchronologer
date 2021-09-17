package com.udacity.jwdnd.critterchronologer.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is Entity class for User type Customer and maps Customer table.
 */
@Entity
@Getter @Setter  @NoArgsConstructor // Lombok Auto Creation of Getters Setter and default constructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(nullable = false)
    private String name;

    private String phoneNumber;

    @Nationalized
    @Column(length = 700)
    private String notes;

    @OneToMany(mappedBy = "customer",targetEntity = Pet.class, cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Pet> pets = new ArrayList<>();
}
