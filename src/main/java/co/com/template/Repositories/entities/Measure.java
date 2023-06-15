package co.com.template.Repositories.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="t_measure")
public class Measure implements Serializable {

    private static final long serialVersionUID = 5022341805021141326L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "measure_id")
    private Long measureId;

    @Column(name = "measure_describe")
    private String measureDescribe;


}
