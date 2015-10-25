package clients;

import error.ResponseError;
import transformers.JsonTransformer;

import static spark.Spark.*;

public class ClientController {

    public ClientController(final ClientService clientService) {
        after((req, res) -> {
            res.type("application/json");
        });

        get("/clients", (req, res) -> clientService.getAllClients(), new JsonTransformer());
        get("/clients/:id", (req, res) -> {
            String id = req.params(":id");
            Client client = clientService.getClient(id);
            if (client != null) {
                return client;
            }
            res.status(400);
            return new ResponseError("No client with id '%s' found", id);
        }, new JsonTransformer());

        put("/clients/:id", (req, res) -> {
            String id = req.params(":id");
            return id;
        }, new JsonTransformer());

        post("/users", (req, res) -> clientService.createClient(
                req.queryParams("name"),
                req.queryParams("email")
        ), new JsonTransformer());

        put("/users/:id", (req, res) -> clientService.updateClient(
                req.params(":id"),
                req.queryParams("name"),
                req.queryParams("email")
        ), new JsonTransformer());

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(new JsonTransformer().render(new ResponseError(e)));
        });
    }
}
