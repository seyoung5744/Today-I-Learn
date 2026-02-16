package wonspring.splearn.adapter.integration;

import org.springframework.stereotype.Component;
import wonspring.splearn.application.required.EmailSender;
import wonspring.splearn.domain.Email;

@Component
public class DummyEmailSender implements EmailSender {

    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("DummyEmailSender send email: " + email);
    }
}
