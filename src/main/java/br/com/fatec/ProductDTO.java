package br.com.fatec;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    
    @NotNull(message = "O nome não pode ser nulo")
    @NotEmpty(message = "O nome não pode ser vazio")
    private String name;

    @NotNull(message = "A descrição não pode ser nula")
    @NotEmpty(message = "A descrição não pode ser vazia")
    private String description;

    @NotNull(message = "O preço não pode ser nulo")
    private Double price;

}
