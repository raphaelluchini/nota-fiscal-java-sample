package com.devsolutions.orders;

import com.devsolutions.TaxCalculator;
import com.devsolutions.customer.Customer;
import com.devsolutions.customer.CustomerModel;
import com.devsolutions.products.Product;
import com.devsolutions.products.ProductModel;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.*;

/*
* Considerei que uma nota fiscal é uma ordem feita, já que contém todas as informações necessárias para a emissão da nota.
* A visualização de uma nota é feita totalmente pela view, mas na verdade o objeto é uma ordem.
* Uma ordem pode conter diversos produtos e um cliente.
 */
public class OrderController {
    //Gerencia todas as rotas e chamadas para o model relacionadas as ordens
    //Control  all routes and requests related to orders
    public OrderController(final OrderModel orderModel, final ProductModel productModel, final CustomerModel customerModel) {
        //Lista todas as Ordens(Notas fiscais)
        //List all orders
        get("/orders", (req, res) -> {
            Map map = new HashMap();
            map.put("data", orderModel.getAllOrders());
            map.put("delete", req.queryParams("delete"));
            map.put("create", req.queryParams("create"));

            return new ModelAndView(map, "orders/index.hbs");
        }, new HandlebarsTemplateEngine());

        //Rota para o fomulario que cria uma nova ordem(venda/nota fical)
        //View route to create a new order
        get("/orders/new", (req, res) ->  {
            Map map = new HashMap();
            map.put("customers", customerModel.getAllCustomers());
            map.put("products", productModel.getAllProducts());
            return new ModelAndView(map, "orders/new.hbs");
        }, new HandlebarsTemplateEngine());

        //Vizualiza uma ordem(Nota Fiscal)
        //Show an order
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
                map.put("totalTax", TaxCalculator.calculateTax(order.getOrder_total()));
                map.put("taxPercentage", TaxCalculator.taxPercentage);
            }

            return new ModelAndView(map, "orders/view.hbs");
        }, new HandlebarsTemplateEngine());

        //Metodo post para criar uma nova ordem
        //Post to the server to create an Order
        post("/orders", (req, res) -> {
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

            res.redirect("/orders/"+orderId);
            return null;
        });

        //Deleta uma ordem passando seu ID
        //Deleta an order by ID
        get("/orders/:id/delete", (req, res) -> {
            orderModel.deleteOrder(Integer.parseInt(req.params(":id")));
            res.redirect("/orders");
            return null;
        });
    }
}
