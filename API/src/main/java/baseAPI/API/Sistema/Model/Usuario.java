package baseAPI.API.Sistema.Model;

import baseAPI.API.Sistema.DTO.UsuarioDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Builder
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String sobrenome;

    @JoinColumn(unique = true)
    private String documento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;


    @OneToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    private String telefone;

    private String email;

    @OneToMany
    private List<OrdemServico> ordemServicos;

    public Usuario(UsuarioDTO usuarioDTO)
    {
        this.nome = usuarioDTO.nome();
        this.sobrenome = usuarioDTO.sobrenome();
        this.documento = usuarioDTO.documento();
        this.dataNascimento = usuarioDTO.dataNascimento();
        this.telefone = usuarioDTO.telefone();
        this.email = usuarioDTO.Email();
    }
}
