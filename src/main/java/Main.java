import clients.ClientController;
import clients.ClientModel;
import orders.OrderController;
import orders.OrderModel;
import products.ProductController;
import products.ProductModel;


public class Main {

    public static void main(String[] args) {
        new ClientController(new ClientModel());
        new OrderController(new OrderModel(), new ProductModel(), new ClientModel());
        new ProductController(new ProductModel());
    }
}


