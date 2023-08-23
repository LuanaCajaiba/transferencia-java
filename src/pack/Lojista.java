package pack;

public class Lojista extends Usuario {
    private String nomeLoja;

    public Lojista(String nomeCompleto, String cpfCnpj, String email, String senha, String nomeLoja) {
        super(nomeCompleto, cpfCnpj, email, senha);
        this.nomeLoja = nomeLoja;
    }

    public void exibirPerfil() {
        System.out.println("Nome Completo: " + nomeCompleto);
        System.out.println("CPF/CNPJ: " + cpfCnpj);
        System.out.println("Email: " + email);
        System.out.println("Nome da Loja: " + nomeLoja);
        System.out.println("Tipo: Lojista");
    }
}
