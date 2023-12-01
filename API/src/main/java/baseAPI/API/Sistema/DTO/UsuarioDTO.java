package baseAPI.API.Sistema.DTO;

public record UsuarioDTO(String nome, String sobrenome, String documento,
                        Long cep, String Logradouro, String numero, String Bairro,
                         String cdade, String estado, String telefone, String Email ) {
}
