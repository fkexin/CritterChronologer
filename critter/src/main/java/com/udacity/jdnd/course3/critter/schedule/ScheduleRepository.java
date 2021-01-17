package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // find all schedules by petId
    List<Schedule> findAllPetById(Long petId);

    // find all schedules by employee id
    List<Schedule> findAllEmployeesById(Long employeeId);

}
