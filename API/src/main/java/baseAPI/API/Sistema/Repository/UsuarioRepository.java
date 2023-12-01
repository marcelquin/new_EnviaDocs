package baseAPI.API.Sistema.Repository;

import baseAPI.API.Sistema.Model.OrdemServico;
import baseAPI.API.Sistema.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Usuario findBydocumento(String documento);
}
