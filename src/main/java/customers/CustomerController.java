package customers;

import error.ResponseError;
import transformers.JsonTransformer;

import static spark.Spark.*;

public class CustomerController {

    public CustomerController(final CustomerModel clientModel) {
        get("/customers", (req, res) -> clientModel.getAllCustomers(), new JsonTransformer());
        get("/customers/:id", (req, res) -> {
            String id = req.params(":id");
            Customer client = clientModel.getCustomer(Integer.parseInt(id));
            if (client != null) {
                return client;
            }
            res.status(400);
            return new ResponseError("No client with id '%s' found", id);
        }, new JsonTransformer());


        post("/customers", (req, res) -> {
            clientModel.createCustomer(req.queryParams("name"), req.queryParams("email"));
            res.status(200);
            return null;
        }, new JsonTransformer());

        post("/customers/:id/update", (req, res) -> {
            clientModel.updateCustomer(
                    Integer.parseInt(req.params(":id")),
                    req.queryParams("name"),
                    req.queryParams("email"));
            res.status(200);
            return null;
        }, new JsonTransformer());

        post("/customers/:id/delete", (req, res) -> {
            clientModel.deleteCustomer(Integer.parseInt(req.params(":id")));
            res.status(200);
            return null;
        }, new JsonTransformer());

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(new JsonTransformer().render(new ResponseError(e)));
        });
    }
}
