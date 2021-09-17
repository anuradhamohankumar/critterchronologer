package com.udacity.jwdnd.critterchronologer.repository;

import com.udacity.jwdnd.critterchronologer.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository class for Pet Object or Table to do CURD operations.
 */
@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {}
