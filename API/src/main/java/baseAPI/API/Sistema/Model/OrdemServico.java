package baseAPI.API.Sistema.Model;

import baseAPI.API.Sistema.DTO.OrdenServicoDTO;
import baseAPI.API.Sistema.Enum.StatusOS;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Builder
@Entity
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String nome;

    private  String descrisao;

    @OneToMany
    private List<Documento> documentos;

    private String arquivoDownload;

    @JoinColumn(unique = true)
    private  String codigoVerificacao;

    @Enumerated(EnumType.STRING)
    private StatusOS statusOS;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataEnvio;

    public OrdemServico(OrdenServicoDTO ordenServicoDTO)
    {
        this.nome = ordenServicoDTO.nome();
        this.descrisao = ordenServicoDTO.descrisao();
    }
}
