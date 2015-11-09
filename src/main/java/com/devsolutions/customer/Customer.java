package com.devsolutions.customer;

//Todas as propriedades usadas para fazer o CRUD de um cliente
//All properties used to CRUD an customer
public class Customer {
    private Integer id;
    private String name;
    private String email;
    private String cpf;
    private String address;

    public Customer(Integer id, String name, String email, String cpf, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.address = address;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
