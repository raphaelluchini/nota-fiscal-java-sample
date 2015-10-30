package orders;

import customers.Customer;
import customers.CustomerModel;
import com.google.gson.Gson;
import error.ResponseError;
import handlers.OrderHandler;
import products.Product;
import products.ProductModel;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import transformers.JsonTransformer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class OrderController {

    public OrderController(final OrderModel orderModel, final ProductModel productModel, final CustomerModel customerModel) {
        get("/orders", (req, res) -> {
            Map map = new HashMap();
            map.put("data", orderModel.getAllOrders());
            map.put("delete", req.queryParams("delete"));
            map.put("create", req.queryParams("create"));

            return new ModelAndView(map, "orders/index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/orders/:id", (req, res) -> {
            String id = req.params(":id");
            Order order = orderModel.getOrder(Integer.parseInt(id));
            Map<String, Object> map = new HashMap<>();
            
            if (order != null) {
                List<Product> products = productModel.getProductsByOrderId(order.getId());
                Customer customer = customerModel.getCustomer(order.getCustomer_id());

                map.put("order", order);
                map.put("products", products);
                map.put("customer", customer);
            }

            return new ModelAndView(map, "orders/view.hbs");
        }, new HandlebarsTemplateEngine());

        post("/orders", (req, res) -> {

            OrderHandler response = new Gson().fromJson(req.body(), OrderHandler.class);

            Long orderId = orderModel.createOrder(response.getCustomerId(), new Date());

            for (Product product : response.getProducts()) {
                Product productDetails = productModel.getProduct(product.getId());
                productModel.addProductToOrder(orderId, product.getId(), product.getQuantity(), productDetails.getPrice() * product.getQuantity());
            }
            res.status(200);
            return new ResponseError("Order '%s' has been created", orderId.toString());
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
