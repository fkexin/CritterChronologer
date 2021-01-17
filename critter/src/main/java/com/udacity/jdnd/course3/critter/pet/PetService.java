package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.*;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;

    public Pet save(Pet pet){
        petRepository.save(pet);
        Customer owner = pet.getOwner();
        if (owner != null){
            Long customerId = owner.getId();
            Optional<Customer> customerOptional = customerRepository.findById(customerId);
            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();
                if (customer.getPets() != null) {
                    customer.getPets().add(pet);
                }
                customerRepository.save(customer);
            }
        } else {
            throw new UserNotFoundException("Customer not found. ");
        }
        return pet;
    }

    public Optional<Pet> getPetById(Long id){
        return petRepository.findById(id);
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwnerId(Long ownerId){
        Optional<Customer> customer = customerRepository.findById(ownerId);
        if (customer.isPresent()) {
            List<Pet> pets = customer.get().getPets();
            return pets;
        } else {
            throw new CustomerNotFoundException();
        }
    }

    public Customer getOwnerByPetId(Long petId) {
        return petRepository.findById(petId).get().getOwner();
    }
}
