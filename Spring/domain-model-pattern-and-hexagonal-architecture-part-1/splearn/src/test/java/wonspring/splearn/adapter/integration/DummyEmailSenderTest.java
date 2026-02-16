package wonspring.splearn.adapter.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;
import wonspring.splearn.domain.Email;

import static org.assertj.core.api.Assertions.assertThat;

class DummyEmailSenderTest {

    @Test
    @StdIo
    void dummyEmailSender(StdOut out) {
        DummyEmailSender dummyEmailSender = new DummyEmailSender();

        dummyEmailSender.send(new Email("test@test.com"), "subject", "content");

        assertThat(out.capturedLines()[0]).isEqualTo("DummyEmailSender send email: Email[address=test@test.com]");
    }
}