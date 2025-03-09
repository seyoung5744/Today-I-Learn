package was.v4;

import java.io.IOException;

public class ServerMainV4 {

    private static final int POST = 12345;

    public static void main(String[] args) throws IOException {
        HttpServerV4 server = new HttpServerV4(POST);
        server.start();
    }
}
