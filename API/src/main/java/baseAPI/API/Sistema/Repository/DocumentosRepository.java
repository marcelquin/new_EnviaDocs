package baseAPI.API.Sistema.Repository;

import baseAPI.API.Sistema.Model.Documentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentosRepository extends JpaRepository<Documentos,Long> {
}
