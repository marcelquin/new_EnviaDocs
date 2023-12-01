package baseAPI.API.Sistema.Model;

import baseAPI.API.Sistema.Enum.TipoUsuario;
import jakarta.persistence.*;
import lombok.*;

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

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    @OneToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    private String telefone;

    private String email;

    @OneToMany
    private List<OrdemServico> ordemServicos;
}
