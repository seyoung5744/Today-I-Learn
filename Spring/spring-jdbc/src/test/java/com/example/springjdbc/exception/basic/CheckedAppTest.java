package com.example.springjdbc.exception.basic;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.ConnectException;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class CheckedAppTest {

    @Test
    public void checked() {
        Controller controller = new Controller();
        assertThatThrownBy(() -> controller.request())
            .isInstanceOf(SQLException.class);
    }

    static class Controller {

        Service service = new Service();

        public void request() throws SQLException, ConnectException {
            service.login();
        }
    }

    static class Service {

        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void login() throws SQLException, ConnectException {
            repository.call();
            networkClient.call();
        }
    }

    static class NetworkClient {

        public void call() throws ConnectException {
            throw new ConnectException("연결 실패");
        }
    }

    static class Repository {

        public void call() throws SQLException {
            throw new SQLException("ex");
        }
    }
}
