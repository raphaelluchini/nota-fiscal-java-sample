package com.devsolutions.products;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class ProductController {

    public ProductController(final ProductModel productModel) {

        get("/com/devsolutions/customer/products", (req, res) ->  {
            Map map = new HashMap();
            map.put("data", productModel.getAllProducts());
            map.put("delete", req.queryParams("delete"));
            map.put("create", req.queryParams("create"));
            return new ModelAndView(map, "com/devsolutions/customer/products/index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/com/devsolutions/customer/products/new", (req, res) ->  {
            return new ModelAndView(null, "com/devsolutions/customer/products/new.hbs");
        }, new HandlebarsTemplateEngine());

        post("/com/devsolutions/customer/products", (req, res) -> {
            productModel.createProduct(req.queryParams("name"), Integer.parseInt(req.queryParams("price")), Integer.parseInt(req.queryParams("stock")));
            res.redirect("/com/devsolutions/customer/products");
            return null;
        });

        get("/com/devsolutions/customer/products/:id", (req, res) -> {
            String id = req.params(":id");
            Product product = productModel.getProduct(Integer.parseInt(id));
            Map map = new HashMap();
            map.put("fs", req.queryParams("fs"));
            if(product != null){
                map.put("data", product);
            }
            return new ModelAndView(map, "com/devsolutions/customer/products/edit.hbs");
        }, new HandlebarsTemplateEngine());

        post("/com/devsolutions/customer/products/:id/edit", (req, res) -> {
            productModel.updateProduct(
                    Integer.parseInt(req.params(":id")),
                    req.queryParams("name"),
                    Integer.parseInt(req.queryParams("price")),
                    Integer.parseInt(req.queryParams("stock")));

            res.redirect("/com/devsolutions/customer/products/" + req.params(":id") + "?fs=true");
            return null;
        });

        get("/com/devsolutions/customer/products/:id/delete", (req, res) -> {
            productModel.deleteProduct(Integer.parseInt(req.params(":id")));
            res.redirect("/com/devsolutions/customer/products");
            return null;
        });
    }
}
