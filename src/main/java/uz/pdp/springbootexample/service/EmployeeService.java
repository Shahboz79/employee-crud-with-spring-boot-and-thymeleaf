package uz.pdp.springbootexample.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.springbootexample.dto.EmployeeDto;
import uz.pdp.springbootexample.entity.Employee;
import uz.pdp.springbootexample.entity.Position;

import uz.pdp.springbootexample.projection.EmployeeListProjection;
import uz.pdp.springbootexample.repository.EmployeeRepository;
import uz.pdp.springbootexample.repository.PositionRepository;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    final EmployeeRepository employeeRepository;
    final PositionRepository positionRepository;

    public EmployeeService(EmployeeRepository employeeRepository, PositionRepository positionRepository) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
    }
    private boolean existsById(Integer id) {
        return employeeRepository.existsById(id);
    }


    public Employee saveEmployee( EmployeeDto employeeDto) {

        Integer positionId = employeeDto.getPositionId();
        Optional<Position> optionalPosition = positionRepository.findById(positionId);
        if (optionalPosition.isEmpty()) {
            throw new IllegalStateException("Position not found!!");
        }
//        String filename= StringUtils.cleanPath(file.getOriginalFilename());
//        if(filename.contains("..")){
//            System.out.println("not a valid");
//
//        }
        Employee employee = null;
        employee = Employee
                .builder()
                .fullName(employeeDto.getFullName())
                .position(optionalPosition.get())
                .salary(employeeDto.getSalary())
//                    .image(Base64.getEncoder().encodeToString(file.getBytes()))
                .build();

        employeeRepository.save(employee);
        return employee;
    }

    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    public Employee findId(Integer id){

        return employeeRepository.getOne(id);

    }


    public void updateEmployee(EmployeeDto employeeDto)
            throws Exception {
        if (!StringUtils.isEmpty(employeeDto.getFullName())) {
            if (!existsById(employeeDto.getId())) {
                throw new Exception("Cannot find Contact with id: " + employeeDto.getId());
            }
            Integer positionId = employeeDto.getPositionId();
            Optional<Position> optionalPosition = positionRepository.findById(positionId);
            if (optionalPosition.isEmpty()) {
                throw new IllegalStateException("Position not found!!");
            }
            Employee employee = Employee
                    .builder()
                    .id(employeeDto.getId())
                    .fullName(employeeDto.getFullName())
                    .position(optionalPosition.get())
                    .salary(employeeDto.getSalary())
                    .build();

            employeeRepository.save(employee);
        }
        else {
            Exception exc = new Exception("Failed to save emloyee");

            throw exc;
        }
    }

    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }


    public Page<EmployeeListProjection> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<EmployeeListProjection> all = employeeRepository.getAllEmployees(pageable);
        return all;

    }
}
