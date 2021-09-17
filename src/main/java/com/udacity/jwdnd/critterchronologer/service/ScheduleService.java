package com.udacity.jwdnd.critterchronologer.service;

import com.udacity.jwdnd.critterchronologer.entity.Customer;
import com.udacity.jwdnd.critterchronologer.entity.Pet;
import com.udacity.jwdnd.critterchronologer.entity.Schedule;
import com.udacity.jwdnd.critterchronologer.repository.CustomerRepository;
import com.udacity.jwdnd.critterchronologer.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This is service class for Schedule.
 */
@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    CustomerRepository customerRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Saves the schedule
     * @param schedule
     * @return updated and saved schedule object
     */
    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    /**
     * Returns list of all schedules.
     * @return
     */
    public List<Schedule> findAllSchedules() {
        return scheduleRepository.findAll();
    }

    /**
     * Gets list of schedules for specified pet.
     * @param petId
     * @return list of schedules
     */
    public List<Schedule> findAllByPetsId(Long petId) {
        return scheduleRepository.findAllByPetsId(petId);
    }

    /**
     * Gets list of schedules for specified employee.
     * @param employeeId
     * @return list of schedules
     */
    public List<Schedule> findAllByEmployeesId(Long employeeId) {
        return scheduleRepository.findAllByEmployeesId(employeeId);
    }

    /**
     * Returns schedules for all the pets owned by the specified customer.
     * @param customerId
     * @return list of schedules
     */
    public List<Schedule> getAllSchedulesByCustomerId(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer != null && customer.get() != null) {
            List<Pet> pets = customer.get().getPets();
            List<Schedule> schedules = new ArrayList<>();
            for(Pet p : pets){
                schedules.addAll(scheduleRepository.findAllByPetsId(p.getId()));
            }
            return schedules;
        } else {
            return null;
        }
    }

}
