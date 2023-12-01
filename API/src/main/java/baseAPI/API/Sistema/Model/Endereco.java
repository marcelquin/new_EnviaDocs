package baseAPI.API.Sistema.Model;

import baseAPI.API.Sistema.DTO.UsuarioDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Builder
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cep;

    private String logradouro;

    private String numero;

    private String bairro;

    private String cidade;

    private String estado;

    public Endereco(UsuarioDTO usuarioDTO)
    {
        this.cep = usuarioDTO.cep();
        this.logradouro = usuarioDTO.Logradouro();
        this.numero = usuarioDTO.numero();
        this.bairro = usuarioDTO.Bairro();
        this.cidade = usuarioDTO.cidade();
        this.estado = usuarioDTO.estado();
    }
}
