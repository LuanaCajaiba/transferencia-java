package pack;

public class Carteira {
    private double saldo;

    public Carteira() {
        this.saldo = 0.0;
    }

    public double getSaldo() {
        return saldo;
    }

    public void adicionarFundos(double valor) {
        saldo += valor;
        System.out.println("Fundos adicionados: " + valor);
    }

    public void retirarFundos(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
            System.out.println("Fundos retirados: " + valor);
        } else {
            System.out.println("Saldo insuficiente para retirada.");
        }
    }

    public void receberTransferencia(double valor) {
        saldo += valor;
        System.out.println("Transferência recebida: " + valor);
    }

    public void transferir(Carteira destinatario, double valor) {
        if (valor <= saldo) {
            saldo -= valor;
            destinatario.adicionarFundos(valor);
            System.out.println("Transferência realizada: " + valor);
        } else {
            System.out.println("Saldo insuficiente para transferência.");
        }
    }
}