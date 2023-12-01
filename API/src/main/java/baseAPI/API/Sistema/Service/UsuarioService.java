package baseAPI.API.Sistema.Service;

import baseAPI.API.Sistema.DTO.EnderecoEditDTO;
import baseAPI.API.Sistema.DTO.UsuarioDTO;
import baseAPI.API.Sistema.DTO.UsuarioEditDTO;
import baseAPI.API.Sistema.Enum.SelecionarEventoBackup;
import baseAPI.API.Sistema.Enum.TipoUsuario;
import baseAPI.API.Sistema.Model.Backup;
import baseAPI.API.Sistema.Model.Endereco;
import baseAPI.API.Sistema.Model.Usuario;
import baseAPI.API.Sistema.Repository.BackupRepository;
import baseAPI.API.Sistema.Repository.EnderecoRepository;
import baseAPI.API.Sistema.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    EnderecoRepository enderecoRepository;
    @Autowired
    BackupRepository backupRepository;

    public ResponseEntity<Usuario> listarUsuario() throws Exception
    {
        try
        {
            return (ResponseEntity<Usuario>) usuarioRepository.findAll();
        }
        catch (Exception e)
        {
            throw new Exception("erro ao listar");
        }
    }

    public ResponseEntity<Usuario> BuscarUsuarioPorId(Long id) throws Exception
    {
        try
        {
            if(usuarioRepository.existsById(id))
            {
                Usuario usuario = usuarioRepository.findById(id).get();
                return new ResponseEntity<>(usuario, HttpStatus.OK);
            }

        }
        catch (Exception e)
        {
            throw new Exception("erro ao listar");
        }
        return null;
    }

    public ResponseEntity<UsuarioDTO> Salvar(UsuarioDTO usuarioDTO) throws Exception
    {
        try
        {
            if(usuarioDTO != null)
            {
                Endereco endereco = new Endereco(usuarioDTO);
                Usuario usuario = new Usuario(usuarioDTO);
                Backup backup = new Backup();

                enderecoRepository.save(endereco);
                usuario.setEndereco(endereco);
                usuarioRepository.save(usuario);
                backup.setSelecionarEventoBackup(SelecionarEventoBackup.USUARIO_CRIADO);
                backup.setUsuario(usuario);
                backup.setDataAcao(LocalDateTime.now());
                backupRepository.save(backup);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e)
        {
            throw new Exception("erro ao salvar");
        }
    }

    public ResponseEntity<UsuarioDTO> EditarUsuario(Long idUsuario, UsuarioEditDTO usuarioDTO) throws Exception
    {
       try
       {
           if(usuarioRepository.existsById(idUsuario))
           {
                Usuario usuario = usuarioRepository.findById(idUsuario).get();
                Backup backup = new Backup();
                usuario.setNome(usuarioDTO.nome());
                usuario.setSobrenome(usuarioDTO.sobrenome());
                usuario.setTelefone(usuarioDTO.telefone());
                usuario.setEmail(usuarioDTO.Email());
                usuarioRepository.save(usuario);
                backup.setSelecionarEventoBackup(SelecionarEventoBackup.USUARIO_EDITADO);
                backup.setUsuario(usuario);
                backup.setDataAcao(LocalDateTime.now());
                backupRepository.save(backup);
               return new ResponseEntity<>(HttpStatus.OK);
           }
           else
           {
               return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
           }
       }
       catch (Exception e)
       {
           throw new Exception("erro ao salvar");
       }
    }

    public ResponseEntity<EnderecoEditDTO> AlterarEndereco(Long idUsuario, EnderecoEditDTO enderecoEditDTO) throws Exception
    {
        try
        {
            if(usuarioRepository.existsById(idUsuario))
            {
                Usuario usuario = usuarioRepository.findById(idUsuario).get();
                if(enderecoRepository.existsById(usuario.getEndereco().getId()))
                {
                    Endereco endereco = enderecoRepository.findById(usuario.getEndereco().getId()).get();
                    Backup backup = new Backup();
                    endereco.setCep(enderecoEditDTO.cep());
                    endereco.setLogradouro(enderecoEditDTO.Logradouro());
                    endereco.setNumero(enderecoEditDTO.numero());
                    endereco.setBairro(enderecoEditDTO.Bairro());
                    endereco.setCidade(enderecoEditDTO.cidade());
                    endereco.setEstado(enderecoEditDTO.estado());
                    enderecoRepository.save(endereco);
                    backup.setSelecionarEventoBackup(SelecionarEventoBackup.USUARIO_EDITADO);
                    backup.setUsuario(usuario);
                    backup.setDataAcao(LocalDateTime.now());
                    backupRepository.save(backup);
                    return new ResponseEntity<>(HttpStatus.OK);
                }



            }
            else
            {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e)
        {
            throw new Exception("erro ao salvar");
        }
        return null;
    }

    public ResponseEntity<UsuarioDTO> DeletarUsuario(Long id) throws Exception
    {
        try
        {
            if(usuarioRepository.existsById(id))
            {
                usuarioRepository.deleteById(id);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            throw new Exception("erro ao deletar");
        }
    }

}
