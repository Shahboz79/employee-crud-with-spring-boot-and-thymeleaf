package uz.pdp.springbootexample.controller;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.springbootexample.dto.EmployeeDto;
import uz.pdp.springbootexample.entity.Employee;
import uz.pdp.springbootexample.entity.Position;
import uz.pdp.springbootexample.projection.EmployeeListProjection;
import uz.pdp.springbootexample.service.AttachmentService;
import uz.pdp.springbootexample.service.EmployeeService;
import uz.pdp.springbootexample.service.PositionService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final PositionService positionService;
    private final EmployeeService employeeService;
    private final AttachmentService attachmentService;

    public EmployeeController(
            PositionService positionService,
            EmployeeService employeeService,
            AttachmentService attachmentService) {
        this.positionService = positionService;
        this.employeeService = employeeService;
        this.attachmentService = attachmentService;
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



    @GetMapping("/add")
    public String showSignUpForm(EmployeeDto employeeDto) {
        return "add-employee";
    }


    @PostMapping
    public String saveEmployee(@Valid EmployeeDto employeeDto, BindingResult bindingResult,@RequestParam("file") MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()) {
            return "add-employee";
        }
        Employee employee = employeeService.saveEmployee(employeeDto);
        attachmentService.uploadFile(employee,file);
        return "redirect:/employees";
    }
//    @GetMapping("/getFile/{id}")
//    private void getFile(@PathVariable Integer id, HttpServletResponse response) throws IOException {
//        attachmentService.getFileFromDb(id, response);
//    }

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
    @GetMapping("")
    public String viewHomePage(Model model) {
        return findPaginated(1, model);
    }

    @GetMapping("/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 5;

        Page<EmployeeListProjection> page = employeeService.findPaginated(pageNo, pageSize);
        List < EmployeeListProjection > listEmployees = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listEmployees", listEmployees);
        return "employee";
    }


}
