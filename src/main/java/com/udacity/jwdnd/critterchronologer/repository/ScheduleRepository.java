package com.udacity.jwdnd.critterchronologer.repository;

import com.udacity.jwdnd.critterchronologer.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository class for Schedule Object or Table to do CURD operations.
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    /**
     * This method returns all schedules for the specified pet.
     * @param petId pet_id
     * @return list of schedules
     */
    List<Schedule> findAllByPetsId(Long petId);

    /**
     * This method returns all schedules for the specified employee.
     * @param employeeId
     * @return list of schedules
     */
    List<Schedule> findAllByEmployeesId(Long employeeId);
}
