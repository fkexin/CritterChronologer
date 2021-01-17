package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // find all schedules by petId
    //use _ to access th value of a property that is inside another entity (Pets)
    List<Schedule> findAllByPets_Id(Long petId);

    // find all schedules by employee id
    List<Schedule> findAllByEmployees_Id(Long employeeId);

}
