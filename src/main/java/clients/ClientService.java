package clients;

import java.util.List;

public class ClientService {

    // returns a list of all users
    public List<Client> getAllClients() {
        return null;
    }

    // returns a single user by id
    public Client getClient(String id) {
        Client client = new Client("raphael", "email@mail.com");
        return client;
    }

    // creates a new user
    public Client createClient(String name, String email) {
        return null;
    }

    // updates an existing user
    public Client updateClient(String id, String name, String email) {
        return null;
    }
}
