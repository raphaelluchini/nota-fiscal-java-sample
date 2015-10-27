package orders;

import clients.Client;
import clients.ClientModel;
import error.ResponseError;
import products.Product;
import products.ProductModel;
import transformers.JsonTransformer;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
            Map map = new JsonTransformer().toJson(req.body());
            ArrayList<Integer> products = new ArrayList<Integer>();
            //TODO: Use real values

//            for(Integer productId : (ArrayList<Integer>) map.get("products")){
//            }
            products.add(2);

            Long orderId = orderModel.createOrder(Integer.parseInt(map.get("costumerId").toString()));

            for(Integer productId : products){
                productModel.addProductToOrder(orderId, productId, 2, 1000);
            }
            res.status(200);
            return new ResponseError(map.get("products").toString());
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
