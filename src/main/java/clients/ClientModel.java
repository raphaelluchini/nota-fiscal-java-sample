package clients;

import database.MySQLAdapter;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class ClientModel{

    public List<Client> getAllClients(){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "SELECT hash, name, email FROM clients";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).executeAndFetch(Client.class);
        }
    }

    public  List<Client> getClient(String hash){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "SELECT hash, name, email FROM clients WHERE hash= :hash";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql)
                .addParameter("hash", hash)
                .executeAndFetch(Client.class);
        }
    }

    public void createClient(String hash, String name, String email){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "INSERT INTO clients(hash, name, email) VALUES (:hash, :name, :email)";
        try(Connection con = mysql.open()) {
            con.createQuery(sql)
                    .addParameter("hash", hash)
                    .addParameter("name", name)
                    .addParameter("email", email)
                    .executeUpdate();
        }
    }
}
