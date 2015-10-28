package products;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class ProductController {

    public ProductController(final ProductModel productModel) {

        get("/products", (req, res) ->  {
            Map map = new HashMap();
            map.put("data", productModel.getAllProducts());
            map.put("delete", req.queryParams("delete"));
            map.put("create", req.queryParams("create"));
            return new ModelAndView(map, "products/index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/products/new", (req, res) ->  {
            return new ModelAndView(null, "products/new.hbs");
        }, new HandlebarsTemplateEngine());

        post("/products", (req, res) -> {
            productModel.createProduct(req.queryParams("name"), Integer.parseInt(req.queryParams("price")), Integer.parseInt(req.queryParams("stock")));
            res.redirect("/products?create=true");
            return null;
        });

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

        post("/products/:id/edit", (req, res) -> {
            productModel.updateProduct(
                    Integer.parseInt(req.params(":id")),
                    req.queryParams("name"),
                    Integer.parseInt(req.queryParams("price")),
                    Integer.parseInt(req.queryParams("stock")));

            res.redirect("/products/" + req.params(":id") + "?fs=true");
            return null;
        });

        get("/products/:id/delete", (req, res) -> {
            productModel.deleteProduct(Integer.parseInt(req.params(":id")));
            res.redirect("/products?delete=true");
            return null;
        });
    }
}
