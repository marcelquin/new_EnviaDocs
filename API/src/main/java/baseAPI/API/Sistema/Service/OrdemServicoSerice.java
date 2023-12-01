package baseAPI.API.Sistema.Service;

import baseAPI.API.Sistema.DTO.OrdenServicoDTO;
import baseAPI.API.Sistema.Enum.SelecionarEventoBackup;
import baseAPI.API.Sistema.Enum.StatusOS;
import baseAPI.API.Sistema.Model.Backup;
import baseAPI.API.Sistema.Model.Documentos;
import baseAPI.API.Sistema.Model.OrdemServico;
import baseAPI.API.Sistema.Model.Usuario;
import baseAPI.API.Sistema.Repository.BackupRepository;
import baseAPI.API.Sistema.Repository.DocumentosRepository;
import baseAPI.API.Sistema.Repository.OrdemServicoRepository;
import baseAPI.API.Sistema.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

import static java.nio.file.Paths.get;
import static org.apache.tomcat.util.http.fileupload.FileUploadBase.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Service
public class OrdemServicoSerice {

    private static String caminhoImagem = "D:\\PROJETOS JAVA\\PROJETOS\\new_EnviaDocs\\API\\Upload\\OS_Arquivos\\";
    private static String caminhoImagemzip = "D:\\PROJETOS JAVA\\PROJETOS\\new_EnviaDocs\\API\\Upload\\Download\\";

    @Autowired
    OrdemServicoRepository ordemServicoRepository;
    @Autowired
    DocumentosRepository documentosRepository;
    @Autowired
    BackupRepository backupRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    public ResponseEntity<OrdemServico> listarUsuario() throws Exception
    {
        try
        {
            return (ResponseEntity<OrdemServico>) ordemServicoRepository.findAll();
        }
        catch (Exception e)
        {
            throw new Exception("erro ao listar");
        }
    }

    public ResponseEntity<OrdemServico> BuscarUsuarioPorId(Long id) throws Exception
    {
        try
        {
            if(ordemServicoRepository.existsById(id))
            {
                OrdemServico ordemServico = ordemServicoRepository.findById(id).get();
                return new ResponseEntity<>(ordemServico, OK);
            }

        }
        catch (Exception e)
        {
            throw new Exception("erro ao listar");
        }
        return null;
    }

    public ResponseEntity<OrdenServicoDTO> NovaOrdemServico(Long idUsuario,OrdenServicoDTO ordenServicoDTO)
    {
        try{
            if(usuarioRepository.existsById(idUsuario))
            {
                Usuario usuario = usuarioRepository.findById(idUsuario).get();
                if(ordenServicoDTO != null)
                {
                    OrdemServico ordemServico = new OrdemServico(ordenServicoDTO);
                    Documentos documentos = new Documentos();
                    documentosRepository.save(documentos);
                    Backup backup = new Backup();
                    ordemServico.setDataCriacao(LocalDateTime.now());
                    int cod = (int) (10000001 + Math.random() * 89999999);
                    String codigo = "OS_"+cod;
                    ordemServico.setCodigoVerificacao(codigo);
                    ordemServico.setUsuario(Usuario.builder().build());
                    ordemServico.setDocumentos(documentos);
                    ordemServico.setStatusOS(StatusOS.AGUARDANDO_RECEBIMENTO);
                    ordemServicoRepository.save(ordemServico);
                    usuario.getOrdemServicos().add(ordemServico);
                    usuarioRepository.save(usuario);
                    backup.setSelecionarEventoBackup(SelecionarEventoBackup.ORDEM_SERVICO_CRIADA);
                    backup.setOrdemServico(ordemServico);
                    backup.setUsuario(usuario);
                    backup.setDataAcao(LocalDateTime.now());
                    backupRepository.save(backup);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                }
            }
            else
            {
                return new ResponseEntity<>(BAD_REQUEST);
            }
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }

    public ResponseEntity<OrdenServicoDTO> EnviarDocumentos(Long idCliente, String coditoOS, MultipartFile[] files)
    {
        try{
            if(usuarioRepository.existsById(idCliente))
            {
                Usuario usuario = usuarioRepository.findById(idCliente).get();
                OrdemServico ordemServico = ordemServicoRepository.findBycodigoVerificacao(coditoOS);
                if(ordemServico != null)
                {
                    Documentos documentos = documentosRepository.findById(ordemServico.getDocumentos().getId()).get();
                    Backup backup = new Backup();
                    List<String> arquivos = new ArrayList<>();
                    boolean pasta = new File(caminhoImagem + "\\"+ordemServico.getCodigoVerificacao()).mkdir();

                    for(MultipartFile file : files)
                    {
                        byte[] bytes = file.getBytes();
                        Path caminho = get(caminhoImagem+ordemServico.getCodigoVerificacao()+"\\"+file.getOriginalFilename());
                        Files.write(caminho, bytes);
                        arquivos.add(file.getOriginalFilename());
                    }
                    documentos.setArquivos(arquivos);
                    documentosRepository.save(documentos);
                    ordemServico.setDataEnvio(LocalDateTime.now());
                    ordemServico.setStatusOS(StatusOS.RECEBIDO);
                    ordemServicoRepository.save(ordemServico);
                    backup.setSelecionarEventoBackup(SelecionarEventoBackup.DOCUMENTOS_ENVIADOS);
                    backup.setOrdemServico(ordemServico);
                    backup.setUsuario(usuario);
                    backup.setDataAcao(LocalDateTime.now());
                    backupRepository.save(backup);
                    ziparArquivos(ordemServico.getCodigoVerificacao());
                    return new ResponseEntity<>(OK);
                }
            }
            else
            {
                return new ResponseEntity<>(BAD_REQUEST);
            }
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }

    public ResponseEntity<OrdenServicoDTO> ziparArquivos(String codigo)throws IOException
    {
        try {
            OrdemServico ordemServico = ordemServicoRepository.findBycodigoVerificacao(codigo);
            if(ordemServico != null)
            {
                Documentos documentos = documentosRepository.findById(ordemServico.getDocumentos().getId()).get();
                String sourceFile = caminhoImagem + codigo + "\\";
                FileOutputStream fos = new FileOutputStream(caminhoImagemzip +codigo+".zip");
                documentos.setArquivoDownload(codigo+".zip");
                documentosRepository.save(documentos);
                ZipOutputStream zipOut = new ZipOutputStream(fos);
                File fileToZip = new File(sourceFile);
                String fileName = fileToZip.getName();
                UtilService.zipFile(fileToZip, fileToZip.getName(), zipOut);
                zipOut.close();
                fos.close();
                return new ResponseEntity<>(OK);
            }
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getMessage();
        }
        return null;
    }

    public ResponseEntity<Resource> downloadFiles(String codigo) throws IOException
    {

        try{
            if(codigo != null)
            {
                OrdemServico ordemServico = ordemServicoRepository.findBycodigoVerificacao(codigo);
                if(ordemServico != null)
                {
                    String filename = ordemServico.getDocumentos().getArquivoDownload();
                    Path filePath = get(caminhoImagemzip).toAbsolutePath().normalize().resolve(filename);
                    if (!Files.exists(filePath)) {
                        throw new FileNotFoundException(filename + " was not found on the server");
                    }
                    Backup backup = new Backup();
                    backup.setDataAcao(LocalDateTime.now());
                    backup.setSelecionarEventoBackup(SelecionarEventoBackup.ARQUIVO_DOWNLOAD);
                    backup.setOrdemServico(ordemServico);
                    backupRepository.save(backup);
                    Resource resource = new UrlResource(filePath.toUri());
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add("File-Name", filename);
                    httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
                    return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                            .headers(httpHeaders).body(resource);
                }

            }
            else
            {
                return new ResponseEntity<>(BAD_REQUEST);
            }
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }

    public ResponseEntity<OrdenServicoDTO> DeletarOrdem(Long idOrdem)
    {
        try{
            if(ordemServicoRepository.existsById(idOrdem))
            {
                OrdemServico ordemServico = ordemServicoRepository.findById(idOrdem).get();
                for(String fileName : ordemServico.getDocumentos().getArquivos())
                {
                    removeArquivo(fileName);
                }
                removeArquivo(ordemServico.getDocumentos().getArquivoDownload());
                ordemServicoRepository.deleteById(idOrdem);
                return new ResponseEntity<>(OK);
            }
            else
            {
                return new ResponseEntity<>(BAD_REQUEST);
            }
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }

    public void removeArquivo (String fileName)
    {
        File file = new File(fileName);
        file.delete();
    }
}
