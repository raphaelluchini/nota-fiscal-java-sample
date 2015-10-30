package orders;

import customers.Customer;
import customers.CustomerModel;
import com.google.gson.Gson;
import error.ResponseError;
import handlers.OrderHandler;
import products.Product;
import products.ProductModel;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.template.handlebars.HandlebarsTemplateEngine;
import transformers.JsonTransformer;

import java.util.*;

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

        get("/orders/new", (req, res) ->  {
            Map map = new HashMap();
            map.put("customers", customerModel.getAllCustomers());
            map.put("products", productModel.getAllProducts());
            return new ModelAndView(map, "orders/new.hbs");
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
            Long orderId = orderModel.createOrder(Integer.parseInt(req.queryParams("customer")), new Date());

            Integer i = 0;
            while (req.queryParams("product[" + i + "]") != null){
                Integer pId = Integer.parseInt(req.queryParams("product[" + i + "]"));
                Product  p = productModel.getProduct(pId);
                productModel.addProductToOrder(
                        orderId,
                        pId,
                        Integer.parseInt(req.queryParams("product[" + i + "][0]")),
                        p.getPrice());
                i +=1;
            }

            res.redirect("/orders?create=true");
            return null;
        });


        get("/orders/:id/delete", (req, res) -> {
            orderModel.deleteOrder(Integer.parseInt(req.params(":id")));
            res.redirect("/orders?delete=true");
            return null;
        });
    }
}
