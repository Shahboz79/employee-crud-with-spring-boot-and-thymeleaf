package uz.pdp.springbootexample.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springbootexample.dto.EmployeeDto;
import uz.pdp.springbootexample.entity.Employee;
import uz.pdp.springbootexample.entity.Position;
import uz.pdp.springbootexample.service.EmployeeService;
import uz.pdp.springbootexample.service.PositionService;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {


    private final PositionService positionService;
    private final EmployeeService employeeService;

    public EmployeeController(
            PositionService positionService,
            EmployeeService employeeService
    ) {
        this.positionService = positionService;
        this.employeeService = employeeService;
    }


    @ModelAttribute(name = "employee")
    public Employee getEmployee() {
        return new Employee();
    }

    @ModelAttribute(name = "employeeList")
    public List<Employee> getEmployeeList() {
        return employeeService.getAllEmployees();
    }

    @ModelAttribute(name = "positionList")
    public List<Position> getPositionList() {
        return positionService.getAllPositions();
    }


    @GetMapping
    public String getAllEmployees() {
        return "employee";
    }


    @PostMapping
    public String saveEmployee(@Valid EmployeeDto employeeDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
        }
        employeeService.saveEmployee(employeeDto);

        return "redirect:/employees";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Employee employe = employeeService.findId(id);
        EmployeeDto employee=new EmployeeDto(
                employe.getId(),employe.getFullName(),employe.getPosition().getId(),employe.getSalary());
        model.addAttribute("employee", employee);
        return "edit-employee";
    }
    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") Integer id, @Valid EmployeeDto employeeDto,
                             BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            employeeDto.setId(id);
            return "edit-employee";
        }

        employeeService.updateEmployee(employeeDto);
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id, Model model) {
        Employee employee = employeeService.findId(id);
        employeeService.deleteEmployee(employee);
        return "redirect:/employees";
    }


}
