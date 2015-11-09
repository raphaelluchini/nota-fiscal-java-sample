package com.devsolutions.customer;

import com.devsolutions.orders.Order;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
//Esta classe contém todas as operações com o banco, relacionado a tabela 'customers', nao use regras de negócio aqui.
//This file contains all SQL operations related with table 'customers', do not use business rule here
public class CustomerModel {

    public Sql2o mysql = null;

    public CustomerModel(Sql2o mysql) {
        this.mysql = mysql;
    }

    public List<Customer> getAllCustomers(){
        String sql = "SELECT * FROM customers";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).executeAndFetch(Customer.class);
        }
    }

    public Customer getCustomer(Integer id){
        String sql = "SELECT id, name, email FROM customers WHERE id= :id";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql)
                .addParameter("id", id)
                .executeAndFetchFirst(Customer.class);
        }
    }

    public void createCustomer(String name, String email, String cpf, String address){
        String sql = "INSERT INTO customers(name, email, cpf, address) VALUES (:name, :email, :cpf, :address)";
        try(Connection con = mysql.open()) {
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("email", email)
                    .addParameter("cpf", cpf)
                    .addParameter("address", address)
                    .executeUpdate();
        }
    }

    public void updateCustomer(Integer id, String name, String email, String cpf, String address){
        String sql = "UPDATE customers SET name= :name, email= :email, cpf= :cpf, address= :address WHERE id=:id";
        try(Connection con = mysql.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .addParameter("email", email)
                    .addParameter("cpf", cpf)
                    .addParameter("address", address)
                    .executeUpdate();
        }
    }

    public void deleteCustomer(Integer id){
        String sql = "SELECT * FROM orders WHERE customer_id=:customers_id";

        String sql1 = "DELETE FROM order_products WHERE orders_id=:orders_id";

        String sql2 = "DELETE FROM orders WHERE id=:id";

        String sql3 = "DELETE FROM customers WHERE id=:id";
        try(Connection con = mysql.open()) {
            Order order = con.createQuery(sql)
                    .addParameter("customers_id", id)
                    .executeAndFetchFirst(Order.class);

            if(order != null){
                con.createQuery(sql1)
                    .addParameter("orders_id", order.getId())
                    .executeUpdate();
                con.createQuery(sql2)
                        .addParameter("id",  order.getId())
                        .executeUpdate();

            }

            con.createQuery(sql3)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
}
