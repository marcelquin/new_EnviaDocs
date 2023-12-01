package baseAPI.API.Sistema.Controller;

import baseAPI.API.Sistema.DTO.EnderecoEditDTO;
import baseAPI.API.Sistema.DTO.UsuarioDTO;
import baseAPI.API.Sistema.DTO.UsuarioEditDTO;
import baseAPI.API.Sistema.Model.Usuario;
import baseAPI.API.Sistema.Service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
@Tag(name = "api/usuario", description = "Manipula dados da tabela")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Operation(summary = "Busca Registros na tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops! algo deu errado"),
    })
    @GetMapping()
    public ResponseEntity <List<Usuario>> listarUsuario() throws Exception
    {return usuarioService.listarUsuario();}

    @Operation(summary = "Busca Registro na tabela por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops! algo deu errado"),
    })
    @GetMapping("/BuscarUsuarioPorId")
    public ResponseEntity<Usuario> BuscarUsuarioPorId(@RequestParam Long id) throws Exception
    {return usuarioService.BuscarUsuarioPorId(id);}

    @Operation(summary = "Salva Novo registro na tabela", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro Salvo com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops! algo deu errado"),
    })
    @PostMapping()
    public ResponseEntity<UsuarioDTO> NovoUsuario(UsuarioDTO usuarioDTO) throws Exception
    {return usuarioService.Salvar(usuarioDTO);}

    @Operation(summary = "Edita Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editado realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops! algo deu errado"),
    })
    @PutMapping()
    public ResponseEntity<UsuarioDTO> EditarUsuario(@RequestParam Long idUsuario, UsuarioEditDTO usuarioDTO) throws Exception
    {return usuarioService.EditarUsuario(idUsuario, usuarioDTO);}

    @Operation(summary = "Edita Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editado realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops! algo deu errado"),
    })
    @PutMapping("/AlterarEndereco")
    public ResponseEntity<EnderecoEditDTO> AlterarEndereco(@RequestParam Long idUsuario, EnderecoEditDTO enderecoEditDTO) throws Exception
    {return usuarioService.AlterarEndereco(idUsuario, enderecoEditDTO);}


    @Operation(summary = "Deleta Registro na tabela", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops! algo deu errado"),
    })
    @DeleteMapping()
    public ResponseEntity<UsuarioDTO> DeletarUsuario(@RequestParam Long id) throws Exception
    {return usuarioService.DeletarUsuario(id);}

}
