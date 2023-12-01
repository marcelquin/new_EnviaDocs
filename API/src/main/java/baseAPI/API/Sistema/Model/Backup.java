package baseAPI.API.Sistema.Model;

import baseAPI.API.Sistema.Enum.SelecionarEventoBackup;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Builder
@Entity
public class Backup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    SelecionarEventoBackup selecionarEventoBackup;

    @ManyToOne
    @JoinColumn(name = "backup_usuario_Id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "backup_ordemServico_Id")
    private OrdemServico ordemServico;

}
