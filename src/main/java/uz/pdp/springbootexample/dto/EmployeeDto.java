package uz.pdp.springbootexample.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.springbootexample.entity.Position;

import javax.persistence.*;
import javax.validation.constraints.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeDto {


    private Integer id;

//    @NotNull
//    @NotBlank
    @NotEmpty(message = "Ubu narsa yoz baraka topkur....")
    @Size(min = 5, max = 250,message = "5 va 250 oraligidagi matn kiriting ")
    private String fullName;

    @NotNull(message = "Position tanlash shart!!!")
    private Integer positionId;

    @NotNull(message = "Employee's salary cannot be null.")
    @Min(value = 18)
    private Double salary;

//    private MultipartFile image;

}