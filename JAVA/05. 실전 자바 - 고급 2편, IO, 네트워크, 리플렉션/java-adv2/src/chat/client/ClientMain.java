package chat.client;

import java.io.IOException;

public class ClientMain {

    private static final int POST = 12345;

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", POST);
        client.start();
    }
}
