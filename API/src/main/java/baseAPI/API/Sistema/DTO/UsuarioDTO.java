package baseAPI.API.Sistema.DTO;

import lombok.Data;

import java.time.LocalDate;

public record UsuarioDTO(String nome, String sobrenome, String documento, LocalDate dataNascimento,
                        Long cep, String Logradouro, String numero, String Bairro,
                         String cidade, String estado, String telefone, String Email ) {
}
