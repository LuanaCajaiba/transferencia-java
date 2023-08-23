import usuarios.Usuario;
package usuarios;
public class Main {
    public static void main(String[] args) {
        UsuarioComum usuarioComum = new Usuario.UsuarioComum("João", "joao@example.com");
        Lojista lojista = new Usuario.Lojista("Maria", "maria@example.com", "Loja da Maria");

        System.out.println("Perfil do Usuário Comum:");
        usuarioComum.exibirPerfil();

        System.out.println("\nPerfil do Lojista:");
        lojista.exibirPerfil();
    }
}
