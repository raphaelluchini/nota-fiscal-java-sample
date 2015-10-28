package orders;

import database.MySQLAdapter;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Date;
import java.util.List;

public class OrderModel {

    public List<Order> getAllOrders(){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "SELECT o.id, date, "+
                "COUNT(*) products_in_order, "+
                "SUM(op.quantity * op.price) order_total "+
                "FROM orders o JOIN order_products op "+
                "ON o.id = op.order_id "+
                "GROUP BY o.id";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).executeAndFetch(Order.class);
        }
    }

    public Order getOrder(Integer id){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "SELECT * FROM orders WHERE id= :id";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql)
                .addParameter("id", id)
                .executeAndFetchFirst(Order.class);
        }
    }

    public Long createOrder(Integer costumerId, Date date){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "INSERT INTO orders(costumer_id, date) VALUES (:costumerId,:date)";
        try(Connection con = mysql.open()) {
            return (Long) con.createQuery(sql)
                    .addParameter("costumerId", costumerId)
                    .addParameter("date", date)
                    .executeUpdate()
                    .getKey();
        }
    }

    public void deleteOrder(Integer id){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "DELETE FROM orders WHERE id=:id";
        try(Connection con = mysql.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
}
