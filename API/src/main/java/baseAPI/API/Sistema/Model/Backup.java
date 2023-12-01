package baseAPI.API.Sistema.Model;

import baseAPI.API.Sistema.Enum.SelecionarEventoBackup;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataAcao;

}
