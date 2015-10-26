package clients;

import java.util.List;
import java.util.UUID;

public class ClientService {
    public ClientModel clientModel = new ClientModel();

    // returns a list of all users
    public List<Client> getAllClients() {
        return clientModel.getAllClients();
    }

    // returns a single user by id
    public Client getClient(String hash) {
        List<Client> clients = clientModel.getClient(hash);
        return clients.get(0);
    }

    // creates a new user
    public void createClient(String name, String email){
        clientModel.createClient(UUID.randomUUID().toString(), name, email);
    }
}
