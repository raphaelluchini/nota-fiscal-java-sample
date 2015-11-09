package com.devsolutions.outgoing;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

//Esta classe contém todas as operações com o banco, relacionado a tabela 'outgoing', nao use regras de negócio aqui.
//This file contains all SQL operations related with table 'outgoing', do not use business rule here
public class OutgoingModel {
    public Sql2o mysql = null;

    public OutgoingModel(Sql2o mysql) {
        this.mysql = mysql;
    }

    public List<Outgoing> getAllOutgoing(){
        String sql = "SELECT * FROM outgoing";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).executeAndFetch(Outgoing.class);
        }
    }

    public Outgoing getAllOutgoingPrice(){
        String sql = "SELECT SUM(price) as price FROM outgoing";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).executeAndFetchFirst(Outgoing.class);
        }
    }

    public Outgoing getOutgoing(Integer id){
        String sql = "SELECT * FROM outgoing WHERE id= :id";

        try(Connection con = mysql.open()) {
            return con.createQuery(sql)
                .addParameter("id", id)
                .executeAndFetchFirst(Outgoing.class);
        }
    }

    public Long createOutgoing(String name, Double price){
        String sql = "INSERT INTO outgoing(name, price) VALUES (:name,:price)";

        try(Connection con = mysql.open()) {
            return (Long) con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("price", price)
                    .executeUpdate()
                    .getKey();
        }
    }

    public void updateOutgoing(Integer id, String name, Integer price){
        String sql = "UPDATE outgoing SET name=:name, price=:price WHERE id=:id";

        try(Connection con = mysql.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .addParameter("price", price)
                    .executeUpdate();
        }
    }

    public void deleteOutgoing(Integer id){
        String sql = "DELETE FROM outgoing WHERE id=:id";
        try(Connection con = mysql.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

}
