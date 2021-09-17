package com.udacity.jwdnd.critterchronologer.pet;

import com.udacity.jwdnd.critterchronologer.entity.Customer;
import com.udacity.jwdnd.critterchronologer.entity.Pet;
import com.udacity.jwdnd.critterchronologer.service.CustomerService;
import com.udacity.jwdnd.critterchronologer.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;
    @Autowired
    CustomerService customerService;

    public PetController(PetService petService,CustomerService customerService) {
        this.petService = petService;
        this.customerService=customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        if(petDTO == null) return null;
        Pet petSaved = petService.savePet(convertPetDTOToPet(petDTO));
        if(petDTO.getOwnerId() != 0) {
            Customer customer = customerService.getCustomerById(petDTO.getOwnerId());
            if(customer.getPets() == null) {
                List<Pet> pets = new ArrayList<>();
                customer.setPets(pets);
            }
            customer.getPets().add(petSaved);
            customerService.saveCustomer(customer);
        }
        petDTO.setId(petSaved.getId());
        return petDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.findById(petId);
        return convertPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.retrieveAllPets();
        List<PetDTO> petDTOS = pets.stream().map(p->convertPetToPetDTO(p)).collect(Collectors.toList());
        return petDTOS;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetsByOwner(ownerId);
        List<PetDTO> petDTOS = pets.stream().map(p->convertPetToPetDTO(p)).collect(Collectors.toList());
        return petDTOS;
    }

    /**
     * Helper method to convert Pet Entity to Pet DTO.
     * @param pet DAO Object
     * @return petDTO Transition Object
     */
    private PetDTO convertPetToPetDTO(Pet pet) {
        if(pet != null) {
            PetDTO petDTO = new PetDTO();
            BeanUtils.copyProperties(pet,petDTO);
            if(pet.getCustomer() != null)
                petDTO.setOwnerId(pet.getCustomer().getId());
            return petDTO;
        }
        return null;
    }

    /**
     * Helper method to convert Pet DTO to Pet Entity.
     * @param petDTO Transition Object
     * @return pet DAO Object
     */
    private Pet convertPetDTOToPet(PetDTO petDTO) {
        if(petDTO != null) {
            Pet pet= new Pet();
            BeanUtils.copyProperties(petDTO,pet);
            Customer customer = customerService.getCustomerById(petDTO.getOwnerId());
            pet.setCustomer(customer);
            return pet;
        }
        return null;
    }

}
