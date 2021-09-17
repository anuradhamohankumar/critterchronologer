package com.udacity.jwdnd.critterchronologer.schedule;

import com.udacity.jwdnd.critterchronologer.entity.Employee;
import com.udacity.jwdnd.critterchronologer.entity.Pet;
import com.udacity.jwdnd.critterchronologer.entity.Schedule;
import com.udacity.jwdnd.critterchronologer.service.EmployeeService;
import com.udacity.jwdnd.critterchronologer.service.PetService;
import com.udacity.jwdnd.critterchronologer.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    PetService petService;
    @Autowired
    EmployeeService employeeService;

    public ScheduleController(ScheduleService scheduleService, PetService petService, EmployeeService employeeService) {
        this.scheduleService = scheduleService;
        this.petService = petService;
        this.employeeService = employeeService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        if(scheduleDTO == null) return null;
        Schedule schedule = scheduleService.saveSchedule(convertScheduleDTOToSchedule(scheduleDTO));
        scheduleDTO.setId(schedule.getId());
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.findAllSchedules();
        List<ScheduleDTO> scheduleDTOS = schedules.stream().map(s->convertScheduleToScheduleDTO(s)).collect(Collectors.toList());
        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.findAllByPetsId(petId);
        List<ScheduleDTO> scheduleDTOS = schedules.stream().map(s->convertScheduleToScheduleDTO(s)).collect(Collectors.toList());
        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.findAllByEmployeesId(employeeId);
        List<ScheduleDTO> scheduleDTOS = schedules.stream().map(s->convertScheduleToScheduleDTO(s)).collect(Collectors.toList());
        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getAllSchedulesByCustomerId(customerId);
        List<ScheduleDTO> scheduleDTOS = schedules.stream().map(s->convertScheduleToScheduleDTO(s)).collect(Collectors.toList());
        return scheduleDTOS;
    }

    /**
     * Helper method to convert Schedule Entity to Schedule DTO.
     * @param schedule DAO Object
     * @return scheduleDTO Transition Object
     */
    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        if(schedule == null) return null;
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule,scheduleDTO);
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        return scheduleDTO;
    }

    /**
     * Helper method to convert ScheduleDTO to Entity.
     * @param scheduleDTO Transition Object
     * @return schedule DAO object
     */
    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        if(scheduleDTO == null) return null;
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO,schedule);
        List<Long> employeeIDs = scheduleDTO.getEmployeeIds();
        List<Employee> employees = employeeService.getAllEmployeesByIds(employeeIDs);
        schedule.setEmployees(employees);
        List<Long> petIDs = scheduleDTO.getPetIds();
        List<Pet> pets = petService.getAllPetsByIds(petIDs);
        schedule.setPets(pets);
        return schedule;
    }
}
