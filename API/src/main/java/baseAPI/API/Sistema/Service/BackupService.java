package baseAPI.API.Sistema.Service;

import baseAPI.API.Sistema.Model.Backup;
import baseAPI.API.Sistema.Model.Usuario;
import baseAPI.API.Sistema.Repository.BackupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BackupService {

    @Autowired
    BackupRepository backupRepository;

    public ResponseEntity<Backup> listarUsuario() throws Exception
    {
        try
        {
            return (ResponseEntity<Backup>) backupRepository.findAll();
        }
        catch (Exception e)
        {
            throw new Exception("erro ao listar");
        }
    }

    public ResponseEntity<Backup> BuscarUsuarioPorId(Long id) throws Exception
    {
        try
        {
            if(backupRepository.existsById(id))
            {
               Backup backup = backupRepository.findById(id).get();
                return new ResponseEntity<>(backup, HttpStatus.OK);
            }

        }
        catch (Exception e)
        {
            throw new Exception("erro ao listar");
        }
        return null;
    }
}
