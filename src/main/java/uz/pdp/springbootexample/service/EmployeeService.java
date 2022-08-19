package uz.pdp.springbootexample.service;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uz.pdp.springbootexample.dto.EmployeeDto;
import uz.pdp.springbootexample.entity.Employee;
import uz.pdp.springbootexample.entity.Position;
import uz.pdp.springbootexample.repository.EmployeeRepository;
import uz.pdp.springbootexample.repository.PositionRepository;

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


    public void saveEmployee(EmployeeDto employeeDto) {

        Integer positionId = employeeDto.getPositionId();
        Optional<Position> optionalPosition = positionRepository.findById(positionId);
        if (optionalPosition.isEmpty()) {
            throw new IllegalStateException("Position not found!!");
        }
        Employee employee = Employee
                .builder()
                .fullName(employeeDto.getFullName())
                .position(optionalPosition.get())
                .salary(employeeDto.getSalary())
                .build();

        employeeRepository.save(employee);
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
}
