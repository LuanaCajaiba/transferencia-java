package pack;

public class Usuario {
    protected String nomeCompleto;
    protected String cpfCnpj;
    protected String email;
    protected String senha;
    protected Carteira carteira;

    public Usuario(String nomeCompleto, String cpfCnpj, String email, String senha) {
        this.nomeCompleto = nomeCompleto;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.senha = senha;
        this.carteira = new Carteira();
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public Carteira getCarteira() {
        return carteira;
    }
}
