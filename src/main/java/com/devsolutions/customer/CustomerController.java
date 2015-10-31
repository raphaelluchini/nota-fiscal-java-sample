package com.devsolutions.customer;


import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class CustomerController {

    public CustomerController(final CustomerModel customerModel) {
        get("/customers", (req, res) -> {
            Map map = new HashMap();
            map.put("data", customerModel.getAllCustomers());
            map.put("delete", req.queryParams("delete"));
            map.put("create", req.queryParams("create"));
            return new ModelAndView(map, "customers/index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/customers/new", (req, res) -> {
            return new ModelAndView(null, "customers/new.hbs");
        }, new HandlebarsTemplateEngine());

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

        post("/customer", (req, res) -> {
            customerModel.createCustomer(req.queryParams("name"), req.queryParams("email"));
            res.redirect("/customer");
            return null;
        });

        post("/customers/:id/edit", (req, res) -> {
            customerModel.updateCustomer(
                    Integer.parseInt(req.params(":id")),
                    req.queryParams("name"),
                    req.queryParams("email"));

            res.redirect("/customers/" + req.params(":id") + "?fs=true");
            return null;
        });

        get("/customers/:id/delete", (req, res) -> {
            customerModel.deleteCustomer(Integer.parseInt(req.params(":id")));
            res.redirect("/customer");
            return null;
        });
    }
}
