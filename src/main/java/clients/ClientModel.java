package clients;

import database.MySQLAdapter;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class ClientModel{

    public List<Client> getAllClients(){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "SELECT id, name, email FROM costumers";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).executeAndFetch(Client.class);
        }
    }

    public Client getClient(Integer id){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "SELECT id, name, email FROM costumers WHERE id= :id";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql)
                .addParameter("id", id)
                .executeAndFetchFirst(Client.class);
        }
    }

    public void createClient(String name, String email){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "INSERT INTO costumers(name, email) VALUES (:name, :email)";
        try(Connection con = mysql.open()) {
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("email", email)
                    .executeUpdate();
        }
    }

    public void updateClient(Integer id, String name, String email){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "UPDATE costumers SET name= :name, email= :email WHERE id=:id";
        try(Connection con = mysql.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .addParameter("email", email)
                    .executeUpdate();
        }
    }

    public void deleteClient(Integer id){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "DELETE FROM costumers WHERE id=:id";
        try(Connection con = mysql.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
}
