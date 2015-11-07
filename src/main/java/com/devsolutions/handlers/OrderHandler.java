package com.devsolutions.handlers;

import com.google.gson.annotations.SerializedName;
import com.devsolutions.products.Product;

import java.util.ArrayList;
/**
 * Essa classe parseia todos os campos recebidos no post de uma ordem, para poder inserir no banco
 * This class parse all Order post data to inserto in the database
*/
public class OrderHandler {
    @SerializedName("data")
    private Data data;

    private static class Data {
        @SerializedName("com/devsolutions/customer/products")
        public ProductData[] products;
        @SerializedName("customerId")
        public Integer customerId;
    }

    private static class ProductData {
        @SerializedName("id")
        public Integer id;
        @SerializedName("quantity")
        public Integer quantity;
        @SerializedName("price")
        public Integer price;
    }

    public Integer getCustomerId() {
        return data.customerId;
    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> out = new ArrayList<Product>();

        for (Integer i = 0; i < data.products.length; i++){
            out.add(new Product(data.products[i].id, data.products[i].quantity));
        }
        return out;
    }
}
