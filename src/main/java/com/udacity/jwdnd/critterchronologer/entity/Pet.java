package com.udacity.jwdnd.critterchronologer.entity;

import com.udacity.jwdnd.critterchronologer.pet.PetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import javax.persistence.*;
import java.time.LocalDate;

/**
 * This is Entity class for Pet and maps Pet table.
 */
@Entity
@Getter  @Setter   @NoArgsConstructor // Lombok Auto Creation of Getters Setter and default constructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PetType type;

    @Nationalized
    private  String name;

    private LocalDate birthDate;

    @Nationalized
    @Column(length = 700)
    private String notes;

    @ManyToOne(targetEntity = Customer.class, fetch=FetchType.EAGER)
    private Customer customer;
        
}
