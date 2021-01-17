package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetController;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import com.udacity.jdnd.course3.critter.user.UserNotFoundException;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.udacity.jdnd.course3.critter.pet.PetController.convertPetToPetDTO;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule s = scheduleService.save(convertScheduleDTOtoSchedule(scheduleDTO));
        return convertScheduleToScheduleDTO(s);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        List<ScheduleDTO> dtos = new ArrayList<>();
        schedules.forEach(schedule -> dtos.add(convertScheduleToScheduleDTO(schedule)));
        return dtos;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getScheduleForPet(petId);
        List<ScheduleDTO> dtos = new ArrayList<>();
        schedules.forEach(schedule -> dtos.add(convertScheduleToScheduleDTO(schedule)));
        return dtos;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable Long employeeId) {
        List<Schedule> schedules = scheduleService.getScheduleForEmployee(employeeId);
        List<ScheduleDTO> dtos = new ArrayList<>();
        //schedules.forEach(schedule -> dtos.add(convertScheduleToScheduleDTO(schedule)));
        for (Schedule schedule : schedules)
        {
            dtos.add(convertScheduleToScheduleDTO(schedule));
        }
        return dtos;
    }
    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable Long customerId) {
        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);
        List<ScheduleDTO> dtos = new ArrayList<>();
        schedules.forEach(schedule -> dtos.add(convertScheduleToScheduleDTO(schedule)));
        return dtos;
    }

    private Schedule convertScheduleDTOtoSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        List<Employee> employees = new ArrayList<>();
        for(Long employeeId : scheduleDTO.getEmployeeIds()){
            Optional<Employee> e = employeeService.findEmployeeById(employeeId);
            if (e.isPresent()){
                employees.add(e.get());
            } else {
                throw new UserNotFoundException("Employee not found, please try again.");
            }
        }
        schedule.setEmployees(employees);
        List<Pet> pets = new ArrayList<>();
        for(Long petId : scheduleDTO.getPetIds()){
            Optional<Pet> p = petService.getPetById(petId);
            if (p.isPresent()){
                pets.add(p.get());
            } else {
                throw new UserNotFoundException("Pet not found, please try again.");
            }
        }
        schedule.setPets(pets);
        return schedule;
    }

    private static ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO dto = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, dto);
        List<Pet> pets = schedule.getPets();
        List<Long> petIds = new ArrayList<>();
        for (Pet p : pets) {
            petIds.add(p.getId());
        }
        dto.setPetIds(petIds);

        List<Employee> employees = schedule.getEmployees();
        List<Long> employeeIds = new ArrayList<>();
        for (Employee e : employees) {
            employeeIds.add(e.getId());
        }
        dto.setEmployeeIds(employeeIds);
        return dto;
    }
}
