package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private PetService petService;

    @GetMapping
    public CustomerDTO getCustomerDTO(Customer customer){
        return convertCustomerToCustomerDTO(customer);
    }

    private static CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(customer,dto);
        List<Pet> pets = customer.getPets();
        List<Long> petIds = new ArrayList<>();
        for (Pet p : pets){
            petIds.add(p.getId());
        }
        dto.setPetIds(petIds);
        return dto;
    }

    private Customer convertCustomerDTOtoCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        customer.setPets(petService.getPetsByOwnerId(customerDTO.getId()));
        return customer;
    }

}
