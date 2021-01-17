package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import com.udacity.jdnd.course3.critter.user.UserNotFoundException;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petService.save(convertPetDTOtoPet(petDTO));
        return convertPetToPetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Optional<Pet> pet = petService.getPetById(petId);
        if (pet.isPresent()){
            return convertPetToPetDTO(pet.get());
        } else {
            throw new PetNotFoundException();
        }
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        List<PetDTO> dtos = new ArrayList<>();
        pets.forEach(pet -> dtos.add(convertPetToPetDTO(pet)));
        return dtos;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetsByOwnerId(ownerId);
        List<PetDTO> dtos = new ArrayList<>();
        if (pets != null){
            pets.forEach(pet -> dtos.add(convertPetToPetDTO(pet)));
        }
        return dtos;
    }

    public static PetDTO convertPetToPetDTO(Pet pet){
        PetDTO dto = new PetDTO();
        //BeanUtils.copyProperties(pet, dto);
        if (pet.getOwner() != null){
            dto.setOwnerId(pet.getOwner().getId());
        }
        dto.setBirthDate(pet.getBirthDate());
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setNotes(pet.getNotes());
        dto.setType(pet.getType());
        return dto;
    }

    private Pet convertPetDTOtoPet(PetDTO petDTO){
        Pet pet = new Pet();
        //BeanUtils.copyProperties(petDTO, pet);
        Optional<Customer> owner = customerService.getCustomerById(petDTO.getOwnerId());
        if (owner.isPresent()) {
            pet.setOwner(owner.get());
        }
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setName(petDTO.getName());
        pet.setNotes(petDTO.getNotes());
        pet.setType(petDTO.getType());
        return pet;
    }
}
