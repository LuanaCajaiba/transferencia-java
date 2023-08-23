package pack;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    private static List<Usuario> usuariosCadastrados = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Selecione uma opção:");
            System.out.println("1. Cadastrar Usuário Comum");
            System.out.println("2. Cadastrar Lojista");
            System.out.println("3. Realizar Transferência");
            System.out.println("4. Receber Pagamento");
            System.out.println("5. Sair");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    cadastrarUsuarioComum(scanner);
                    break;
                case 2:
                    cadastrarLojista(scanner);
                    break;
                case 3:
                    realizarTransferencia(scanner);
                    break;
                case 4:
                    receberPagamento(scanner);
                    break;
                case 5:
                    System.out.println("Saindo do programa.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void cadastrarUsuarioComum(Scanner scanner) {
        System.out.print("Nome Completo: ");
        String nomeCompleto = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        if (cpfJaCadastrado(cpf)) {
            System.out.println("CPF já cadastrado no sistema.");
            return;
        }

        System.out.print("Email: ");
        String email = scanner.nextLine();

        if (emailJaCadastrado(email)) {
            System.out.println("Email já cadastrado no sistema.");
            return;
        }

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        UsuarioComum novoUsuario = new UsuarioComum(nomeCompleto, cpf, email, senha);
        usuariosCadastrados.add(novoUsuario);

        System.out.println("Usuário Comum cadastrado com sucesso.");
    }

    private static void cadastrarLojista(Scanner scanner) {
        System.out.print("Nome Completo: ");
        String nomeCompleto = scanner.nextLine();

        System.out.print("CPF/CNPJ: ");
        String cpfCnpj = scanner.nextLine();

        if (cpfJaCadastrado(cpfCnpj)) {
            System.out.println("CPF/CNPJ já cadastrado no sistema.");
            return;
        }

        System.out.print("Email: ");
        String email = scanner.nextLine();

        if (emailJaCadastrado(email)) {
            System.out.println("Email já cadastrado no sistema.");
            return;
        }

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        System.out.print("Nome da Loja: ");
        String nomeLoja = scanner.nextLine();

        Lojista novoLojista = new Lojista(nomeCompleto, cpfCnpj, email, senha, nomeLoja);
        usuariosCadastrados.add(novoLojista);

        System.out.println("Lojista cadastrado com sucesso.");
    }

    private static boolean cpfJaCadastrado(String cpf) {
        for (Usuario usuario : usuariosCadastrados) {
            if (usuario.getCpfCnpj().equals(cpf)) {
                return true;
            }
        }
        return false;
    }

    private static boolean emailJaCadastrado(String email) {
        for (Usuario usuario : usuariosCadastrados) {
            if (usuario.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    private static void realizarTransferencia(Scanner scanner) {
        System.out.print("Digite o email do remetente: ");
        String emailRemetente = scanner.nextLine();

        Usuario remetente = encontrarUsuarioPorEmail(emailRemetente);
        if (remetente == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        System.out.print("Digite o email do destinatário: ");
        String emailDestinatario = scanner.nextLine();

        Usuario destinatario = encontrarUsuarioPorEmail(emailDestinatario);
        if (destinatario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        if (remetente instanceof Lojista) {
            System.out.println("Lojistas não podem enviar transferências.");
        } else {
            System.out.print("Digite o valor da transferência: ");
            double valor = scanner.nextDouble();
            scanner.nextLine(); // Consumir a quebra de linha

            double saldoRemetente = remetente.getCarteira().getSaldo();

            if (autorizarTransferencia(valor, saldoRemetente)) {
                try {
                    // Iniciar a transação
                    remetente.getCarteira().transferir(destinatario.getCarteira(), valor);

                    // Simulação de possível erro após a transferência
                    if (Math.random() < 0.5) {
                        throw new RuntimeException("Erro de processamento após a transferência.");
                    }

                    System.out.println("Transferência realizada com sucesso.");

                } catch (RuntimeException e) {
                    // Reverter a transferência em caso de erro
                    destinatario.getCarteira().transferir(remetente.getCarteira(), valor);
                    System.out.println("Erro na transferência. Dinheiro revertido.");
                }
            } else {
                System.out.println("Transferência não autorizada.");
            }
        }

        System.out.print("Digite o valor da transferência: ");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        remetente.getCarteira().transferir(destinatario.getCarteira(), valor);
    }


    private static Usuario encontrarUsuarioPorEmail(String email) {
        for (Usuario usuario : usuariosCadastrados) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;
    }

    private static boolean autorizarTransferencia(double valor, double saldoRemetente) {
        try {
            // Simulando a consulta ao serviço autorizador externo
            URL url = new URL("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.readLine();
            reader.close();

            // Analisar a resposta (simulação simplificada)
            return response.contains("Autorizado") && saldoRemetente >= valor;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void receberPagamento(Scanner scanner) {
        System.out.print("Digite o email do destinatário: ");
        String emailDestinatario = scanner.nextLine();
        Usuario destinatario = encontrarUsuarioPorEmail(emailDestinatario);
        if (destinatario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        System.out.print("Digite o valor do pagamento: ");
        double valorPagamento = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        destinatario.getCarteira().receberTransferencia(valorPagamento);

        // Simulação de chamada a serviço de notificação
        notificarRecebimento(destinatario, valorPagamento);

    }

    private static void notificarRecebimento(Usuario destinatario, double valor) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            String payload = "{"
                    + "\"destinatario\": \"" + destinatario.getEmail() + "\","
                    + "\"valor\": " + valor
                    + "}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://o4d9z.mocklab.io/notify"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Notificação de recebimento enviada com sucesso.");
            } else {
                System.out.println("Erro ao enviar notificação de recebimento.");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao se comunicar com o serviço de notificação.");
        }
    }
}
