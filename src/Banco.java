import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Banco
{
    public Banco()
    {

    }

    public void inserirBanco(String nome, float preco)
    {
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
    }

    public void consultarBanco(int id)
    {
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
}