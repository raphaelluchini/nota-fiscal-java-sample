package com.devsolutions.customer;


import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class CustomerController {

    public CustomerController(final CustomerModel customerModel) {
        get("/com/devsolutions/customer", (req, res) -> {
            Map map = new HashMap();
            map.put("data", customerModel.getAllCustomers());
            map.put("delete", req.queryParams("delete"));
            map.put("create", req.queryParams("create"));
            return new ModelAndView(map, "com/devsolutions/customer/index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/com/devsolutions/customer/new", (req, res) -> {
            return new ModelAndView(null, "com/devsolutions/customer/new.hbs");
        }, new HandlebarsTemplateEngine());

        get("/com/devsolutions/customer/:id", (req, res) -> {
            String id = req.params(":id");
            Customer customer = customerModel.getCustomer(Integer.parseInt(id));
            Map map = new HashMap();
            map.put("fs", req.queryParams("fs"));
            if (customer != null) {
                map.put("data", customer);
            }
            return new ModelAndView(map, "com/devsolutions/customer/edit.hbs");
        }, new HandlebarsTemplateEngine());

        post("/com/devsolutions/customer", (req, res) -> {
            customerModel.createCustomer(req.queryParams("name"), req.queryParams("email"));
            res.redirect("/com/devsolutions/customer");
            return null;
        });

        post("/com/devsolutions/customer/:id/edit", (req, res) -> {
            customerModel.updateCustomer(
                    Integer.parseInt(req.params(":id")),
                    req.queryParams("name"),
                    req.queryParams("email"));

            res.redirect("/com/devsolutions/customer/" + req.params(":id") + "?fs=true");
            return null;
        });

        get("/com/devsolutions/customer/:id/delete", (req, res) -> {
            customerModel.deleteCustomer(Integer.parseInt(req.params(":id")));
            res.redirect("/com/devsolutions/customer");
            return null;
        });
    }
}
