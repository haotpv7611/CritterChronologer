package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.*;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.beans.BeanUtils;
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

    private final CustomerService customerService;
    private final EmployeeService employeeService;

    public UserController(CustomerService customerService,
                          EmployeeService employeeService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){

        CustomerEntity customer = this.customerService.saveCustomer(customerDTO);
        this.convertToCustomerDTO(customer, customerDTO);

        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){

        List<CustomerDTO> customerDTOs = new ArrayList<>();
        List<CustomerEntity> customers = this.customerService.getAllCustomer();
        if (!customers.isEmpty()) {
            for (CustomerEntity customer : customers) {
                CustomerDTO dto = new CustomerDTO();
                this.convertToCustomerDTO(customer, dto);
                customerDTOs.add(dto);
            }
        }

        return customerDTOs;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){

        CustomerEntity customer = this.customerService.findOwnerByPetId(petId);
        CustomerDTO customerDTO = new CustomerDTO();
        this.convertToCustomerDTO(customer, customerDTO);

        return customerDTO;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        EmployeeEntity employee = this.employeeService.saveEmployee(employeeDTO);
        this.convertToEmployeeDTO(employee, employeeDTO);

        return employeeDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {

        EmployeeEntity employee = this.employeeService.getEmployeeById(employeeId);
        EmployeeDTO employeeDTO = new EmployeeDTO();
        this.convertToEmployeeDTO(employee, employeeDTO);

        return employeeDTO;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {

        this.employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {

        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        Set<EmployeeEntity> employees = this.employeeService.findEmployeesForService(employeeDTO);
        if (!employees.isEmpty()) {
            for (EmployeeEntity employee : employees) {
                EmployeeDTO dto = new EmployeeDTO();
                this.convertToEmployeeDTO(employee, dto);
                employeeDTOs.add(dto);
            }
        }

        return employeeDTOs;
    }

    private void convertToCustomerDTO(CustomerEntity source, CustomerDTO target) {

        BeanUtils.copyProperties(source, target);
        List<PetEntity> pets = source.getPets();
        if (pets != null) {
            List<Long> ids = pets.stream()
                    .map(PetEntity::getId)
                    .collect(Collectors.toList());
            target.setPetIds(ids);
        }
    }

    private void convertToEmployeeDTO(EmployeeEntity source, EmployeeDTO target) {

        BeanUtils.copyProperties(source, target);
        Set<SkillEntity> skills = source.getSkills();
        if (skills != null) {
            Set<EmployeeSkill> employeeSkills = skills.stream()
                    .map(SkillEntity::getSkill)
                    .collect(Collectors.toSet());
            target.setSkills(employeeSkills);
        }

        Set<DayAvailableEntity> daysAvailable = source.getDaysAvailable();
        if (daysAvailable != null) {
            Set<DayOfWeek> dayOfWeeks = daysAvailable.stream()
                    .map(DayAvailableEntity::getDayOfWeek)
                    .collect(Collectors.toSet());
            target.setDaysAvailable(dayOfWeeks);
        }
    }
}
