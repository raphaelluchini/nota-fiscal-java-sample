import customers.CustomerController;
import customers.CustomerModel;
import orders.OrderController;
import orders.OrderModel;
import products.ProductController;
import products.ProductModel;


public class Main {

    public static void main(String[] args) {
        new CustomerController(new CustomerModel());
        new OrderController(new OrderModel(), new ProductModel(), new CustomerModel());
        new ProductController(new ProductModel());
    }
}


