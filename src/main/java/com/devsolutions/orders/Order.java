package com.devsolutions.orders;

import java.util.Date;

//Todas as propriedades usadas para fazer o CRUD de uma ordem(venda/nota fiscal)
//All properties used to CRUD an Order
public class Order {
    private Integer id;
    private String hash;
    private String customerName;
    private Integer customers_id;
    private Integer products_in_order;
    private Integer order_total;
    private Date date;
    private Integer price;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getCustomer_id() {
        return customers_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customers_id = customer_id;
    }

    public Integer getProducts_in_order() {
        return products_in_order;
    }

    public void setProducts_in_order(Integer products_in_order) {
        this.products_in_order = products_in_order;
    }

    public Integer getOrder_total() {
        return order_total;
    }

    public void setOrder_total(Integer order_total) {
        this.order_total = order_total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
