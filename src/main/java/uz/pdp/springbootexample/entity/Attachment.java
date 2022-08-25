package uz.pdp.springbootexample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fileOriginalName; //Shahboz.png

    private long size; //2048000  bytda

    private String contentType;// image/png
    @OneToOne
    private Employee employee;

}
