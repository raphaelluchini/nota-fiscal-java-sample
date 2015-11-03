package com.devsolutions;

import com.devsolutions.customer.CustomerController;
import com.devsolutions.customer.CustomerModel;
import com.devsolutions.database.MySQLAdapter;
import com.devsolutions.orders.OrderController;
import com.devsolutions.orders.OrderModel;
import com.devsolutions.products.ProductController;
import com.devsolutions.products.ProductModel;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.*;

public class Main {

    public static void init(MySQLAdapter mySQLAdapter){

        Sql2o mysql = mySQLAdapter.getMysql();

        staticFileLocation("/public");

        get("/", (req, res) -> new ModelAndView(null, "index.hbs"), new HandlebarsTemplateEngine());


        CustomerModel customerModel =  new CustomerModel(mysql);
        OrderModel orderModel = new OrderModel(mysql);
        ProductModel productModel = new ProductModel(mysql);

        new CustomerController(customerModel);
        new OrderController(orderModel, productModel, customerModel);
        new ProductController(productModel);

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.body(new HandlebarsTemplateEngine().render(new ModelAndView(null, "400.hbs")));
        });
    }
    public static void main(MySQLAdapter _mySQLAdapter) {
        init(_mySQLAdapter);
    }

    public static void main(String[] args) {
        init(new MySQLAdapter());
    }
}


