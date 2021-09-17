package com.udacity.jwdnd.critterchronologer.repository;

import com.udacity.jwdnd.critterchronologer.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.DayOfWeek;
import java.util.List;

/**
 * Repository class for Employee Object or Table to do CURD operations.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    /**
     * This method return Employees having available schedule on specified day.
     * @param day week day
     * @return List of Employees
     */
    List<Employee> findAllByDaysAvailableContaining(DayOfWeek day);

}
