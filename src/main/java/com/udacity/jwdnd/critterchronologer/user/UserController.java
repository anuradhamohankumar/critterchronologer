package com.udacity.jwdnd.critterchronologer.user;

import com.udacity.jwdnd.critterchronologer.entity.Customer;
import com.udacity.jwdnd.critterchronologer.entity.Employee;
import com.udacity.jwdnd.critterchronologer.entity.Pet;
import com.udacity.jwdnd.critterchronologer.service.CustomerService;
import com.udacity.jwdnd.critterchronologer.service.EmployeeService;
import com.udacity.jwdnd.critterchronologer.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    PetService petService;

    public UserController(CustomerService customerService, EmployeeService employeeService,PetService petService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        if(customerDTO == null) return null;
        Customer customer = convertCustomerDTOToCustomer(customerDTO);
        customer = customerService.saveCustomer(customer);
        customerDTO.setId(customer.getId());
        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.retrieveAllCustomers();
        List<CustomerDTO> customerDTOS = customers.stream().map(c->convertCustomerToCustomerDTO(c)).collect(Collectors.toList());
        return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.findById(petId);
        Customer customer = pet.getCustomer();
        return convertCustomerToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        if(employeeDTO == null) return null;
        Employee employee = convertEmployeeDTOToEmployee(employeeDTO);
        employee = employeeService.saveEmployee(employee);
        employeeDTO.setId(employee.getId());
        return employeeDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return convertEmployeeToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeService.saveEmployee(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        if(employeeDTO == null) return null;
        Set<EmployeeSkill> skills = employeeDTO.getSkills();
        DayOfWeek day = employeeDTO.getDate().getDayOfWeek();
        List<Employee> employees = employeeService.getEmployeesByAvailability(skills, day);
        if (!employees.isEmpty()) {
            List<EmployeeDTO> employeeDTOS = employees.stream().map(this::convertEmployeeToEmployeeDTO).collect(Collectors.toList());
            return employeeDTOS;
        }
        return null;


    }

    /**
     * Helper method to convert customer Entity to DTO
     * @param customer DAO
     * @return customerDTO Transition object
     */
    private CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        if(customer != null) {
            BeanUtils.copyProperties(customer,customerDTO);
            List<Long> petIds = new ArrayList<>();
            if (!customer.getPets().isEmpty()) {
                petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
            }
            customerDTO.setPetIds(petIds);
            return customerDTO;
        }
        return null;
    }

    /**
     * Helper method to convert Customer DTO to Entity
     * @param customerDTO Transition Object
     * @return customer DAO
     */
    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO){
         if(customerDTO != null) {
            Customer customer = new Customer();
            BeanUtils.copyProperties(customerDTO,customer);
             if (customerDTO.getPetIds() != null) {
                 List<Long> petIds = customerDTO.getPetIds();
                 List<Pet> pets = petService.getAllPetsByIds(petIds);
                 customer.setPets(pets);
             }
             return customer;
        }
        return null;
    }

    /**
     * Helper method to convert  Employee DTO to DAO.
     * @param employeeDTO
     * @return employee DAO object
     */
    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        if(employeeDTO != null) {
            Employee employee = new Employee();
            BeanUtils.copyProperties(employeeDTO,employee);
            return employee;
        }
        return null;
    }

    /**
     * Helper method to convert Employee DAO to DTO.
     * @param employee
     * @return employee DTO object
     */
    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
         if(employee != null) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            BeanUtils.copyProperties(employee, employeeDTO);
            return employeeDTO;
        }
        return null;
    }

}
