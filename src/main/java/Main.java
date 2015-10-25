import clients.ClientController;
import clients.ClientService;


public class Main {

    public static void main(String[] args) {
        new ClientController(new ClientService());
    }
}


