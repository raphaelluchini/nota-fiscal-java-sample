package products;

import database.MySQLAdapter;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

public class ProductModel {

    public List<Product> getAllProducts(){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "SELECT * FROM products";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).executeAndFetch(Product.class);
        }
    }

    public Product getProduct(Integer id){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "SELECT * FROM products WHERE id= :id";

        try(Connection con = mysql.open()) {
            return con.createQuery(sql)
                .addParameter("id", id)
                .executeAndFetchFirst(Product.class);
        }
    }

    public void createProduct(String name, Integer price){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "INSERT INTO products(name, price) VALUES (:name,:price)";

        try(Connection con = mysql.open()) {
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("price", price)
                    .executeUpdate();
        }
    }

    public void addProductToOrder(Long orderId, Integer productId, Integer quantity, Integer price){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "INSERT INTO order_products(order_id, product_id, quantity, price) VALUES (:orderId,:productId,:quantity,:price)";

        try(Connection con = mysql.open()) {
            con.createQuery(sql)
                    .addParameter("orderId", orderId)
                    .addParameter("productId", productId)
                    .addParameter("quantity", quantity)
                    .addParameter("price", price)
                    .executeUpdate();
        }
    }

    public List<Product> getProductsByOrderId(Integer id){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "SELECT * FROM order_products WHERE order_id= :id";

        try(Connection con = mysql.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetch(Product.class);
        }
    }

    public void deleteProduct(Integer id){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "DELETE FROM products WHERE id=:id";
        try(Connection con = mysql.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

}
