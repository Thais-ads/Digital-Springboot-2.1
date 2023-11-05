package br.com.fiap.epictask.Marcas;

import br.com.fiap.epictask.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name="Marcas")
public class Marcas {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank()
    private String title;


    private String description;

    @Min(1) @Max(100)
    private Integer score;

    @Min(0) @Max(100)
    private Integer status;

    @ManyToOne
    private User user;
    
}
