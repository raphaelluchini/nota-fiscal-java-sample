package products;

import error.ResponseError;
import transformers.JsonTransformer;

import static spark.Spark.*;

public class ProductController {

    public ProductController(final ProductModel productModel) {
        after((req, res) -> {
            res.type("application/json");
        });

        get("/products", (req, res) -> productModel.getAllProducts(), new JsonTransformer());

        get("/products/:id", (req, res) -> {
            String id = req.params(":id");
            Product product = productModel.getProduct(Integer.parseInt(id));
            if(product != null){
                return product;
            }
            res.status(400);
            return new ResponseError("No client with id %s' found", id);
        }, new JsonTransformer());

        post("/products", (req, res) -> {
            productModel.createProduct(req.queryParams("name"), Integer.parseInt(req.queryParams("price")));
            res.status(200);
            return new ResponseError("Product '%s' has been created",  req.queryParams("name"));
        }, new JsonTransformer());


        delete("/products/:id", (req, res) -> {
            productModel.deleteProduct(Integer.parseInt(req.params(":id")));
            res.status(200);
            return new ResponseError("Product id: '%s' has been deleted", req.params(":id"));
        }, new JsonTransformer());

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(new JsonTransformer().render(new ResponseError(e)));
        });
    }
}
