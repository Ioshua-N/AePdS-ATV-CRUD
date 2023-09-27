import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sistema
{
    private String user = "admin";
    private String password = "admin";
    private List<Produto> produtos = new ArrayList<>();
    private int id = 1;
    Scanner scanner = new Scanner(System.in);

    public void login()
    {
        boolean validLogin = false;

        while(!validLogin)
        {
            System.out.print("Insira o nome de usuário: ");
            String usuario = scanner.nextLine();
            System.out.print("Insira a senha: ");
            String senha = scanner.nextLine();

            if(usuario.equals(user) && senha.equals(password))
            {
                System.out.println("\nLogin sucedido!");
                exibirMenu();
            }
            else
            {
                System.out.println("\nFalha no login. Usuário ou senha inválidos.\n");
            }
        }
    }

    private void exibirMenu()
    {
        while(true)
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n--------< Menu >--------\n");
            System.out.println("1 - Inserir Produto ->");
            System.out.println("2 - Consultar Produto ->");
            System.out.println("3 - Alterar Produto ->");
            System.out.println("4 - Remover Produto ->");

            System.out.print("\nEscolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao)
            {
                case 1:
                    inserirProduto();
                    break;

                case 2:
                    consultarProduto();
                    break;

                case 3:
                    alterarProduto();
                    break;

                case 4:
                    removerProduto();
                    break;
                default:
                    System.out.println("Opção inválida, favor inserir opção válida.");
            }
        }
    }

    private void inserirProduto()
    {
        System.out.println("\n--------< Inserir Produto >--------\n");
        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o preço do produto: ");
        float preco = scanner.nextFloat();

        // limpar buffer
        scanner.nextLine();

        try
        {
            // make connection
            Connection connection = SQLConnection.open();
            Statement s = connection.createStatement();

            // insert
            s.executeUpdate
                    (
                            "insert into Produto (nome, preco) values ('" + nome + "', '" + preco + "')"
                    );
            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("\nProduto adicionado com sucesso.");
    }

    private void consultarProduto()
    {
        System.out.println("\n--------< Consulta por ID >--------\n");
        System.out.print("Digite o ID do produto a ser consutlado: ");
        int id = scanner.nextInt();

        try
        {
            // make connection
            Connection connection = SQLConnection.open();
            Statement s = connection.createStatement();

            // make query
            ResultSet rs = s.executeQuery("select * from Accounts where id = '" + id + "'");

            // check if the rs has a row
            if (rs.next())
            {
                // show query
                System.out.println("ID: " + rs.getInt("id") + "Name: " + rs.getString("name") + "Balance: " + rs.getFloat("balance"));
            }
            else
            {
                System.out.println("No account found with ID " + id);
            }

            // close resources
            rs.close();
            s.close();
            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void alterarProduto()
    {
        System.out.println("\n--------< Menu >--------\n");
        System.out.print("Digite o ID do produto a ser alterado: ");
        int id = scanner.nextInt();

        // limpar buffer
        scanner.nextLine();

        for (int i = 0; i < produtos.size(); i++)
        {
            Produto produto = produtos.get(i);
            if (produto.getId() == id)
            {
                System.out.println("Nome atual: " + produto.getName());
                System.out.print("Novo nome: ");
                String name = scanner.nextLine();
                produto.setName(name);

                System.out.println("Preco atual: " + produto.getPrice());
                System.out.print("Novo preço: ");
                float preco = scanner.nextFloat();
                produto.setPrice(preco);

                produtos.set(i, produto);
                System.out.println("Produto alterado om sucesso.");
                return;
            }

            System.out.println("Produto não encontrado.");
        }
    }

    private void removerProduto()
    {
        System.out.println("\n--------< Menu >--------\n");
        System.out.print("Digite o ID do produto a ser removido: ");
        int id = scanner.nextInt();

        // limpar buffer
        scanner.nextLine();

        for (int i = 0; i <= produtos.size(); i++)
        {
            Produto produto = produtos.get(i);
            if(produto.getId() == id)
            {
                produtos.remove(id);
                System.out.println("Produto removido com sucesso.");
                return;
            }
        }

        System.out.println("Produto não encontrado.");
    }
}