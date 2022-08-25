package uz.pdp.springbootexample.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springbootexample.dto.EmployeeDto;
import uz.pdp.springbootexample.entity.Employee;
import uz.pdp.springbootexample.entity.Position;
import uz.pdp.springbootexample.service.EmployeeService;
import uz.pdp.springbootexample.service.PositionService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/position")
public class PositionController {
    private final PositionService positionService;
    private final EmployeeService employeeService;

    public PositionController(PositionService positionService, EmployeeService employeeService) {
        this.positionService = positionService;
        this.employeeService = employeeService;
    }


    @ModelAttribute(name = "employee")
    public Employee getEmployee() {
        return new Employee();
    }

    @ModelAttribute(name = "positions")
    public Position getPosition() {
        return new Position();
    }

    @ModelAttribute(name = "employeeList")
    public List<Employee> getEmployeeList() {
        return employeeService.getAllEmployees();
    }

    @ModelAttribute(name = "positionList")
    public List<Position> getPositionList() {
        return positionService.getAllPositions();
    }

    @GetMapping("/get/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
       Position position=positionService.findById(id);
       List<Employee> employeeList=new ArrayList<>();
        List<Employee> allEmployees = employeeService.getAllEmployees();
        for (Employee employee : allEmployees) {
            if (employee.getPosition().getId().equals(position.getId())){
                employeeList.add(employee);
            }
        }
        model.addAttribute( "employeeList",employeeList);
        model.addAttribute("position",position);

        return "get-position";
    }

    @GetMapping
    public String getAllPositions() {
        return "position";
    }

    @PostMapping
    public String savePosition(@Valid Position position, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
        }
        positionService.savePosition(position);

        return "redirect:/position";
    }

}
