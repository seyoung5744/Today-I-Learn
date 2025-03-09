package was.v1;

import java.io.IOException;

public class ServerMain {

    private static final int POST = 12345;

    public static void main(String[] args) throws IOException {
        HttpServerV1 server = new HttpServerV1(POST);
        server.start();
    }
}
