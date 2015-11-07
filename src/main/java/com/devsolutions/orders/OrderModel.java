package com.devsolutions.orders;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Date;
import java.util.List;

//Esta classe contém todas as operações com o banco, relacionado a tabela 'orders', nao use regras de negócio aqui.
//This file contains all SQL operations related with table 'orders', do not use business rule here
public class OrderModel {

    public Sql2o mysql = null;

    public OrderModel(Sql2o mysql) {
        this.mysql = mysql;
    }

    public List<Order> getAllOrders(){
        String sql = "SELECT o.id, o.date," +
                "c.name as customerName," +
                "SUM(price) as order_total " +
                "FROM orders o " +
                "JOIN customers c ON  o.customer_id = c.id " +
                "JOIN order_products op ON o.id = op.order_id GROUP BY o.id";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).executeAndFetch(Order.class);
        }
    }

    public Order getOrder(Integer id){
        String sql = "SELECT o.id, o.customer_id, o.date, SUM(price) as order_total FROM orders o JOIN order_products op ON o.id = op.order_id WHERE o.id=:id GROUP BY o.id";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql)
                .addParameter("id", id)
                .executeAndFetchFirst(Order.class);
        }
    }

    public Long createOrder(Integer customerId, Date date){
        String sql = "INSERT INTO orders(customer_id, date) VALUES (:customerId,:date)";
        try(Connection con = mysql.open()) {
            return (Long) con.createQuery(sql)
                    .addParameter("customerId", customerId)
                    .addParameter("date", date)
                    .executeUpdate()
                    .getKey();
        }
    }

    public void deleteOrder(Integer id){
        String sql = "DELETE FROM order_products WHERE order_id=:id";
        String sql2 = "DELETE FROM orders WHERE id=:id";
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
