package baseAPI.API.Sistema.Controller;

import baseAPI.API.Sistema.DTO.OrdenServicoDTO;
import baseAPI.API.Sistema.Model.OrdemServico;
import baseAPI.API.Sistema.Service.OrdemServicoSerice;
import baseAPI.API.Sistema.Service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/OrdemServico")
@RequiredArgsConstructor
@Tag(name = "api/OrdemServico", description = "Manipula dados da tabela")
public class OrdemServicoController {

    @Autowired
    OrdemServicoSerice ordemServicoSerice;

    @Operation(summary = "Busca Registros na tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops! algo deu errado"),
    })
    @GetMapping()
    public ResponseEntity<OrdemServico> listarUsuario() throws Exception
    {return ordemServicoSerice.listarUsuario();}

    @Operation(summary = "Busca Registro na tabela por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops! algo deu errado"),
    })
    @GetMapping("/BuscarUsuarioPorId")
    public ResponseEntity<OrdemServico> BuscarUsuarioPorId(@RequestParam Long id) throws Exception
    {return ordemServicoSerice.BuscarUsuarioPorId(id);}

    @Operation(summary = "Salva Novo registro na tabela", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro Salvo com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops! algo deu errado"),
    })
    @PostMapping()
    public ResponseEntity<OrdenServicoDTO> NovaOrdemServico(@RequestParam Long idUsuario,OrdenServicoDTO ordenServicoDTO)
    {return ordemServicoSerice.NovaOrdemServico(idUsuario, ordenServicoDTO);}

    @Operation(summary = "Adiciona arquivos ao registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro Salvo com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops! algo deu errado"),
    })
    @PutMapping(value = "/EnviarDocumentos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OrdenServicoDTO> EnviarDocumentos(@RequestParam Long idCliente, @RequestParam String coditoOS, @RequestPart MultipartFile[] files)
    {return ordemServicoSerice.EnviarDocumentos(idCliente, coditoOS, files);}

    @Operation(summary = "Busca Registro na tabela por código e faz o download do arquivo zipado", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops! algo deu errado"),
    })
    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam String codigo) throws IOException
    { return ordemServicoSerice.downloadFiles(codigo);}


    @Operation(summary = "Deleta Registro na tabela", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops! algo deu errado"),
    })
    @DeleteMapping()
    public ResponseEntity<OrdenServicoDTO> DeletarOrdem(@RequestParam Long idOrdem)
    {return ordemServicoSerice.DeletarOrdem(idOrdem);}
}
