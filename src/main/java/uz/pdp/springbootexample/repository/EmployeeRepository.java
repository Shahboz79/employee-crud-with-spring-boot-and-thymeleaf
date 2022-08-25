package uz.pdp.springbootexample.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.springbootexample.entity.Employee;
import uz.pdp.springbootexample.entity.Position;
import uz.pdp.springbootexample.projection.EmployeeListProjection;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(value = "select e.id,\n" +
            "       e.full_name   as fullName,\n" +
            "       e.position_id as positionId,\n" +
            "       p.name        as positionName,\n" +
            "       e.salary,\n" +
            "       a.id          as imageId\n" +
            "from employees e\n" +
            "         join positions p on p.id = e.position_id\n" +
            "         join attachment a on e.id = a.employee_id"
    ,nativeQuery = true)
    Page<EmployeeListProjection> getAllEmployees(Pageable pageable);
}
