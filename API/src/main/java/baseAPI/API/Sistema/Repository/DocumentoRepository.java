package baseAPI.API.Sistema.Repository;

import baseAPI.API.Sistema.Model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento,Long> {
}
