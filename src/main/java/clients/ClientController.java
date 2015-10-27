package clients;

import error.ResponseError;
import transformers.JsonTransformer;

import static spark.Spark.*;

public class ClientController {

    public ClientController(final ClientModel clientModel) {
        after((req, res) -> {
            res.type("application/json");
        });

        get("/clients", (req, res) -> clientModel.getAllClients(), new JsonTransformer());
        get("/clients/:id", (req, res) -> {
            String id = req.params(":id");
            Client client = clientModel.getClient(Integer.parseInt(id));
            if (client != null) {
                return client;
            }
            res.status(400);
            return new ResponseError("No client with id '%s' found", id);
        }, new JsonTransformer());


        post("/clients", (req, res) -> {
            clientModel.createClient(req.queryParams("name"), req.queryParams("email"));
            res.status(200);
            return null;
        }, new JsonTransformer());

        put("/clients/:id", (req, res) -> {
            clientModel.updateClient(
                Integer.parseInt(req.params(":id")),
                req.queryParams("name"),
                req.queryParams("email"));
            res.status(200);
            return null;
        }, new JsonTransformer());

        delete("/clients/:id", (req, res) -> {
            clientModel.deleteClient(Integer.parseInt(req.params(":id")));
            res.status(200);
            return null;
        }, new JsonTransformer());

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(new JsonTransformer().render(new ResponseError(e)));
        });
    }
}
