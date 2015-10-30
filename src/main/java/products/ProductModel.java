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

    public Long createProduct(String name, Integer price, Integer stock){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "INSERT INTO products(name, price, stock) VALUES (:name,:price,:stock)";

        try(Connection con = mysql.open()) {
            return (Long) con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("price", price)
                    .addParameter("stock", stock)
                    .executeUpdate()
                    .getKey();
        }
    }

    public void updateProduct(Integer id, String name, Integer price, Integer stock){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "UPDATE products SET name=:name, price=:price, stock=:stock WHERE id=:id";

        try(Connection con = mysql.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .addParameter("price", price)
                    .addParameter("stock", stock)
                    .executeUpdate();
        }
    }

    public void addProductToOrder(Long orderId, Integer productId, Integer quantity, Integer price){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "INSERT INTO order_products(order_id, product_id, quantity, price) VALUES (:orderId,:productId,:quantity,:price*:quantity)";

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
        String sql = "SELECT p.id, op.quantity, p.name, p.price FROM order_products op JOIN products p ON op.product_id = p.id WHERE order_id=:id";

        try(Connection con = mysql.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetch(Product.class);
        }
    }

    public void deleteProduct(Integer id){
        Sql2o mysql = MySQLAdapter.connectDB();
        String sql = "DELETE FROM order_products WHERE product_id=:id";
        String sql2 = "DELETE FROM products WHERE id=:id";
        try(Connection con = mysql.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();

            con.createQuery(sql2)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

}
