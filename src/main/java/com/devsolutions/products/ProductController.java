package com.devsolutions.products;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

//Gerencia todas as rotas e chamadas para o model relacionadas aos products
//Control  all routes and requests related to products
public class ProductController {

    public ProductController(final ProductModel productModel) {
        //Lista todas os produtos
        //List all products
        get("/products", (req, res) ->  {
            Map map = new HashMap();
            map.put("data", productModel.getAllProducts());
            map.put("delete", req.queryParams("delete"));
            map.put("create", req.queryParams("create"));
            return new ModelAndView(map, "products/index.hbs");
        }, new HandlebarsTemplateEngine());

        //Rota para o fomulario que cria uma novo produto
        //View route to create a new product
        get("/products/new", (req, res) ->  {
            return new ModelAndView(null, "products/new.hbs");
        }, new HandlebarsTemplateEngine());

        //Metodo post para criar uma novo produto
        //Post to the server to create an product
        post("/products", (req, res) -> {
            productModel.createProduct(req.queryParams("name"), Integer.parseInt(req.queryParams("price")), Integer.parseInt(req.queryParams("stock")));
            res.redirect("/products");
            return null;
        });

        //Vizualiza um producto
        //Show an product
        get("/products/:id", (req, res) -> {
            String id = req.params(":id");
            Product product = productModel.getProduct(Integer.parseInt(id));
            Map map = new HashMap();
            map.put("fs", req.queryParams("fs"));
            if(product != null){
                map.put("data", product);
            }
            return new ModelAndView(map, "products/edit.hbs");
        }, new HandlebarsTemplateEngine());

        //Edita um produto
        //Edit a product
        post("/products/:id/edit", (req, res) -> {
            productModel.updateProduct(
                    Integer.parseInt(req.params(":id")),
                    req.queryParams("name"),
                    Integer.parseInt(req.queryParams("price")),
                    Integer.parseInt(req.queryParams("stock")));

            res.redirect("/products/" + req.params(":id") + "?fs=true");
            return null;
        });

        //Deleta um produto
        //Delete a product
        get("/products/:id/delete", (req, res) -> {
            productModel.deleteProduct(Integer.parseInt(req.params(":id")));
            res.redirect("/products");
            return null;
        });
    }
}
