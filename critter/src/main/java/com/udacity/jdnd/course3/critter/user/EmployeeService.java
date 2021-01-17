package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public Optional<Employee> findEmployeeById(Long employeeId){
        return employeeRepository.findById(employeeId);
    }

    public List<Employee> findEmployeesForService(EmployeeRequestDTO requestDTO) {
        DayOfWeek dayOfWeek = requestDTO.getDate().getDayOfWeek();
        Set<EmployeeSkill> skills = requestDTO.getSkills();
        //find employees have all the skills required and available on the day of week
        List<Employee> availableOnDayOfWeek = employeeRepository.findAllByDaysAvailableContaining(dayOfWeek);
        List<Employee> results = new ArrayList<>();
        for (Employee e:availableOnDayOfWeek) {
            if (e.getSkills().containsAll(skills)){
                results.add(e);
            }
        }
        return results;
    }

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId){
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()){
            Employee e = employee.get();
            e.setDaysAvailable(daysAvailable);
            employeeRepository.save(e);
        } else {
            throw new UserNotFoundException("Employee not found. Please try again.");
        }
    }
}
