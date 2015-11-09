package com.devsolutions.outgoing;

import com.devsolutions.orders.OrderModel;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

//Gerencia todas as rotas e chamadas para o model relacionadas as despesas
//Control  all routes and requests related to outgoing
public class OutgoingController {

    public OutgoingController(final OutgoingModel outgoingModel, OrderModel orderModel) {
        //Lista todas os produtos
        //List all outgoing
        get("/outgoing", (req, res) ->  {
            Map map = new HashMap();
            map.put("data", outgoingModel.getAllOutgoing());
            map.put("productTotal", orderModel.getOrderProductsPriceTotal().getPrice());
            if(orderModel.getOrderProductsPriceTotal().getPrice() != null && outgoingModel.getAllOutgoingPrice().getPrice() != null){
                map.put("balanceTotal", orderModel.getOrderProductsPriceTotal().getPrice() - outgoingModel.getAllOutgoingPrice().getPrice());
            }else if(orderModel.getOrderProductsPriceTotal().getPrice() != null){
                map.put("balanceTotal", orderModel.getOrderProductsPriceTotal().getPrice());
            }else{
                map.put("balanceTotal", outgoingModel.getAllOutgoingPrice().getPrice());
            }
            map.put("delete", req.queryParams("delete"));
            map.put("create", req.queryParams("create"));
            return new ModelAndView(map, "outgoing/index.hbs");
        }, new HandlebarsTemplateEngine());

        //Rota para o fomulario que cria uma novo produto
        //View route to create a new product
        get("/outgoing/new", (req, res) ->  {
            return new ModelAndView(null, "outgoing/new.hbs");
        }, new HandlebarsTemplateEngine());

        //Metodo post para criar uma novo produto
        //Post to the server to create an product
        post("/outgoing", (req, res) -> {
            outgoingModel.createOutgoing(req.queryParams("name"), Double.parseDouble(req.queryParams("price")));
            res.redirect("/outgoing");
            return null;
        });

        //Vizualiza um producto
        //Show an product
        get("/outgoing/:id", (req, res) -> {
            String id = req.params(":id");
            Outgoing outgoing = outgoingModel.getOutgoing(Integer.parseInt(id));
            Map map = new HashMap();
            map.put("fs", req.queryParams("fs"));
            if(outgoing != null){
                map.put("data", outgoing);
            }
            return new ModelAndView(map, "outgoing/edit.hbs");
        }, new HandlebarsTemplateEngine());

        //Edita um produto
        //Edit a product
        post("/outgoing/:id/edit", (req, res) -> {
            outgoingModel.updateOutgoing(
                    Integer.parseInt(req.params(":id")),
                    req.queryParams("name"),
                    Integer.parseInt(req.queryParams("price")));

            res.redirect("/outgoing/" + req.params(":id") + "?fs=true");
            return null;
        });

        //Deleta um produto
        //Delete a product
        get("/outgoing/:id/delete", (req, res) -> {
            outgoingModel.deleteOutgoing(Integer.parseInt(req.params(":id")));
            res.redirect("/outgoing");
            return null;
        });
    }
}
