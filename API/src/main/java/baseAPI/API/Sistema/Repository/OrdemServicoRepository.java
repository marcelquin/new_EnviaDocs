package baseAPI.API.Sistema.Repository;

import baseAPI.API.Sistema.Model.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico,Long> {

    Optional<OrdemServico> findBycodigoVerificacao(String codigoVerificacao);
}
