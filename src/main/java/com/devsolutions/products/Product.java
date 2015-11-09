package com.devsolutions.products;

//Todas as propriedades usadas para fazer o CRUD de um produto
//All properties used to CRUD an product
public class Product {
    private Integer id;
    private String orders_id;
    private String products_id;
    private Integer quantity;
    private String name;
    private Integer price;
    private Integer stock;

    public Product(Integer id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrder_id() {
        return orders_id;
    }

    public void setOrder_id(String order_id) {
        this.orders_id = order_id;
    }

    public String getProduct_id() {
        return products_id;
    }

    public void setProduct_id(String product_id) {
        this.products_id = product_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
