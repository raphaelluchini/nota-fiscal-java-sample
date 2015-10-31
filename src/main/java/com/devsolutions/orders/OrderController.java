package com.devsolutions.orders;

import com.devsolutions.customer.Customer;
import com.devsolutions.customer.CustomerModel;
import com.devsolutions.products.Product;
import com.devsolutions.products.ProductModel;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.*;

public class OrderController {

    public OrderController(final OrderModel orderModel, final ProductModel productModel, final CustomerModel customerModel) {
        get("/customer/orders", (req, res) -> {
            Map map = new HashMap();
            map.put("data", orderModel.getAllOrders());
            map.put("delete", req.queryParams("delete"));
            map.put("create", req.queryParams("create"));

            return new ModelAndView(map, "com/devsolutions/customer/orders/index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/customer/orders/new", (req, res) ->  {
            Map map = new HashMap();
            map.put("com/devsolutions/customer", customerModel.getAllCustomers());
            map.put("com/devsolutions/customer/products", productModel.getAllProducts());
            return new ModelAndView(map, "com/devsolutions/customer/orders/new.hbs");
        }, new HandlebarsTemplateEngine());

        get("/customer/orders/:id", (req, res) -> {
            String id = req.params(":id");
            Order order = orderModel.getOrder(Integer.parseInt(id));
            Map<String, Object> map = new HashMap<>();

            if (order != null) {
                List<Product> products = productModel.getProductsByOrderId(order.getId());
                Customer customer = customerModel.getCustomer(order.getCustomer_id());

                map.put("order", order);
                map.put("com/devsolutions/customer/products", products);
                map.put("customer", customer);
            }

            return new ModelAndView(map, "com/devsolutions/customer/orders/view.hbs");
        }, new HandlebarsTemplateEngine());

        post("/customer/orders", (req, res) -> {
            Long orderId = orderModel.createOrder(Integer.parseInt(req.queryParams("customer")), new Date());

            Integer i = 0;
            while (req.queryParams("product[" + i + "]") != null){
                Integer pId = Integer.parseInt(req.queryParams("product[" + i + "]"));
                Product  p = productModel.getProduct(pId);
                int qtd = Integer.parseInt(req.queryParams("product[" + i + "][0]"));

                if(productModel.getProduct(pId).getStock() >= qtd){
                    productModel.addProductToOrder(
                            orderId,
                            pId,
                            qtd,
                            p.getPrice());
                }
                i +=1;
            }

            res.redirect("/customer/orders");
            return null;
        });


        get("/customer/orders/:id/delete", (req, res) -> {
            orderModel.deleteOrder(Integer.parseInt(req.params(":id")));
            res.redirect("/customer/orders");
            return null;
        });
    }
}
