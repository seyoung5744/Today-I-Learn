package hello.jdbc.exception.basic;

import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UncheckedAppTest {

    @Test
    public void unchecked() {
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(() -> controller.request())
            .isInstanceOf(RuntimeSQLException.class);
    }

    @Test
    public void printEx() {
        //given
        Controller controller = new Controller();

        try {
            controller.request();
        } catch (Exception e) {
            // e.printStackTrace();
            log.info("ex", e);
        }

        //when
        //then
    }

    static class Controller {

        Service service = new Service();

        public void request() {
            service.logic();
        }
    }

    static class Service {

        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic() {
            repository.call();
            networkClient.call();
        }
    }

    static class NetworkClient {

        public void call() {
            throw new RuntimeConnectException("연결 실패");
        }
    }

    static class Repository {

        public void call() {
            try {
                runSQL();
            } catch (SQLException e) {
                throw new RuntimeSQLException(e);
            }
        }

        private void runSQL() throws SQLException {
            throw new SQLException("Ex");
        }
    }

    static class RuntimeConnectException extends RuntimeException {

        public RuntimeConnectException(String message) {
            super(message);
        }
    }

    static class RuntimeSQLException extends RuntimeException {

        public RuntimeSQLException() {
        }

        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }
    }
}
