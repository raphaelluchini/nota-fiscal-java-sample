import customers.CustomerController;
import customers.CustomerModel;
import orders.OrderController;
import orders.OrderModel;
import products.ProductController;
import products.ProductModel;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        new CustomerController(new CustomerModel());
        new OrderController(new OrderModel(), new ProductModel(), new CustomerModel());
        new ProductController(new ProductModel());

        get("/", (req, res) -> new ModelAndView(null, "index.hbs"), new HandlebarsTemplateEngine());

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.body(new HandlebarsTemplateEngine().render(new ModelAndView(null, "400.hbs")));
        });
    }
}


