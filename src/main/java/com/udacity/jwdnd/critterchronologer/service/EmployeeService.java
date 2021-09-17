package com.udacity.jwdnd.critterchronologer.service;

import com.udacity.jwdnd.critterchronologer.entity.Employee;
import com.udacity.jwdnd.critterchronologer.repository.EmployeeRepository;
import com.udacity.jwdnd.critterchronologer.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * This is service class for Employee.
 */
@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Saves Employee details in the datbase.
     * @param employee to be saved
     * @return updated saved Employee object
     */
    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    /**
     * Returns specified Employee.
     * @param employeeId
     * @return employee object
     */
    public Employee getEmployeeById(Long employeeId){
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if(employeeOpt != null) return employeeOpt.get();
        else return null;
    }

    /**
     * Returns all the Employees in the database.
     * @return list of employees
     */
    public List<Employee> retrieveAllEmployees(){
        return (List<Employee>) employeeRepository.findAll();
    }

    /**
     * Returns list of employees for the specified list of employee ids.
     * @param employeeIds
     * @return list of employees
     */
    public List<Employee> getAllEmployeesByIds(List<Long> employeeIds) {
        return employeeRepository.findAllById(employeeIds);
    }

    /**
     * Returns employees who are available for specified day and have the skills.
     * @param skills employee skills
     * @param day day of the week
     * @return list of employees
     */
    public List<Employee> getEmployeesByAvailability(Set<EmployeeSkill> skills, DayOfWeek day) {
        List<Employee> availableEmployeesOnDay = employeeRepository.findAllByDaysAvailableContaining(day);
        List<Employee> availableEmployeesOnDayWSkills = new ArrayList<>();
        for (Employee employee : availableEmployeesOnDay) {
            if (employee.getSkills().containsAll(skills)) {
                availableEmployeesOnDayWSkills.add(employee);
            }
        }
        return availableEmployeesOnDayWSkills;
    }

    /**
     * Deletes Employee for the specified id.
     * @param id
     */
    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }
}

