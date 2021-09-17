package com.udacity.jwdnd.critterchronologer.service;

import com.udacity.jwdnd.critterchronologer.entity.Customer;
import com.udacity.jwdnd.critterchronologer.entity.Pet;
import com.udacity.jwdnd.critterchronologer.repository.CustomerRepository;
import com.udacity.jwdnd.critterchronologer.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * This is service class for Pet.
 */
@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Returns pets for specified list of pet ids.
     * @param ids
     * @return list of pets
     */
    public List<Pet> getAllPetsByIds(List<Long> ids){
        return (List<Pet>) petRepository.findAllById(ids);
    }

    /**
     * Returns Pet for specified id.
     * @param id
     * @return pet
     */
    public Pet findById(Long id){
       Optional<Pet>  pet = petRepository.findById(id);
       if(pet != null) return pet.get();
        return null;
    }

    /**
     * Returns all pets from database.
     * @return list of pets
     */
    public List<Pet> retrieveAllPets(){
        return (List<Pet>) petRepository.findAll();
    }

    /**
     * Saves Pet in the database.
     * @param pet
     * @return updated saved pet object
     */
    public Pet savePet(Pet pet){
        return petRepository.save(pet);
    }

    /**
     * Returns list of pets owned by the specified customer
     * @param customerId
     * @return list of pets
     */
   public List<Pet> getPetsByOwner(Long customerId) {
       Optional<Customer> customer = customerRepository.findById(customerId);
       if(customer != null && customer.get() != null) {
           List<Pet> pets = customer.get().getPets();
           return pets;
       } else {
           return null;
       }
    }

    /**
     * Deletes Pet for the specified id.
     * @param id
     */
    public void deletePet(Long id){
        petRepository.deleteById(id);
    }
}
