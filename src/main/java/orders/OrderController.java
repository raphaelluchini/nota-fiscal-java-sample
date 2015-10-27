package orders;

import clients.Client;
import clients.ClientModel;
import error.ResponseError;
import products.Product;
import products.ProductModel;
import transformers.JsonTransformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class OrderController {

    public OrderController(final OrderModel orderModel, final ProductModel productModel, final ClientModel clientModel) {
        after((req, res) -> {
            res.type("application/json");
        });

        get("/orders", (req, res) -> orderModel.getAllOrders(), new JsonTransformer());

        get("/orders/:id", (req, res) -> {
            String id = req.params(":id");
            Order order = orderModel.getOrder(Integer.parseInt(id));

            if (order != null) {
                List<Product> products = productModel.getProductsByOrderId(order.getId());
                Client customer = clientModel.getClient(order.getCustomer_id());

                Map<String, Object> map = new HashMap<>();
                map.put("order", order);
                map.put("products", products);
                map.put("customer", customer);

                return map;
            }
            res.status(400);
            return new ResponseError("No client with id '%s' found", id);
        }, new JsonTransformer());

        post("/orders", (req, res) -> {
//            orderModel.createOrder(UUID.randomUUID().toString());
            res.status(200);
            return null;
        }, new JsonTransformer());


        delete("/orders/:id", (req, res) -> {
            orderModel.deleteOrder(Integer.parseInt(req.params(":id")));
            res.status(200);
            return null;
        }, new JsonTransformer());

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(new JsonTransformer().render(new ResponseError(e)));
        });
    }
}
