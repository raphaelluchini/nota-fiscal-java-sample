package com.devsolutions;

import com.devsolutions.customer.CustomerController;
import com.devsolutions.customer.CustomerModel;
import com.devsolutions.orders.OrderController;
import com.devsolutions.orders.OrderModel;
import com.devsolutions.products.ProductController;
import com.devsolutions.products.ProductModel;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        staticFileLocation("/public");

        get("/", (req, res) -> new ModelAndView(null, "index.hbs"), new HandlebarsTemplateEngine());


        new CustomerController(new CustomerModel());
        new OrderController(new OrderModel(), new ProductModel(), new CustomerModel());
        new ProductController(new ProductModel());

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.body(new HandlebarsTemplateEngine().render(new ModelAndView(null, "400.hbs")));
        });
    }
}

