package pack;

public class UsuarioComum extends Usuario {
    public UsuarioComum(String nomeCompleto, String cpfCnpj, String email, String senha) {
        super(nomeCompleto, cpfCnpj, email, senha);
    }

    public void exibirPerfil() {
        System.out.println("Nome Completo: " + nomeCompleto);
        System.out.println("CPF: " + cpfCnpj);
        System.out.println("Email: " + email);
        System.out.println("Tipo: Usuário Comum");
    }
}
