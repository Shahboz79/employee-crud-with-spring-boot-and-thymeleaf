package uz.pdp.springbootexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.springbootexample.entity.Employee;
import uz.pdp.springbootexample.entity.Position;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
