package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
   // @Query("SELECT e FROM Employee e WHERE :day MEMBER OF e.daysAvailable")
    List<Employee> findAllByDaysAvailableContaining(DayOfWeek day);
}
