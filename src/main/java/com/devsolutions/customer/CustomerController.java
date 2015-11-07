package com.devsolutions.customer;


import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

//Gerencia todas as rotas e chamadas para o model relacionadas aos customers
//Control  all routes and requests related to customer
public class CustomerController {
    public CustomerController(final CustomerModel customerModel) {
        //Retorna todos clientes
        //Returns all clients
        get("/customers", (req, res) -> {
            Map map = new HashMap();
            map.put("data", customerModel.getAllCustomers());
            map.put("delete", req.queryParams("delete"));
            map.put("create", req.queryParams("create"));
            return new ModelAndView(map, "customers/index.hbs");
        }, new HandlebarsTemplateEngine());

        //Rota para a view com o form para criar um cliente
        //Route to a view with a form to create a customer
        get("/customers/new", (req, res) -> {
            return new ModelAndView(null, "customers/new.hbs");
        }, new HandlebarsTemplateEngine());

        //Retorna um cliente usando um ID
        //Returns a customer using his ID
        get("/customers/:id", (req, res) -> {
            String id = req.params(":id");
            Customer customer = customerModel.getCustomer(Integer.parseInt(id));
            Map map = new HashMap();
            map.put("fs", req.queryParams("fs"));
            if (customer != null) {
                map.put("data", customer);
            }
            return new ModelAndView(map, "customers/edit.hbs");
        }, new HandlebarsTemplateEngine());
        //Rota post que cria um cliente
        //Post url to create a customer
        post("/customers", (req, res) -> {
            customerModel.createCustomer(req.queryParams("name"), req.queryParams("email"));
            res.redirect("/customers");
            return null;
        });
        //Rota post para editar um cliente usando seu ID
        //Post url to edit a customer, using his ID
        post("/customers/:id/edit", (req, res) -> {
            customerModel.updateCustomer(
                    Integer.parseInt(req.params(":id")),
                    req.queryParams("name"),
                    req.queryParams("email"));

            res.redirect("/customers/" + req.params(":id") + "?fs=true");
            return null;
        });
        //Rota para deletar um cliente usando seu ID
        //Get route to delete a customer using his ID
        get("/customers/:id/delete", (req, res) -> {
            customerModel.deleteCustomer(Integer.parseInt(req.params(":id")));
            res.redirect("/customers");
            return null;
        });
    }
}
