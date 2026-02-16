package wonspring.splearn;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import wonspring.splearn.application.required.EmailSender;
import wonspring.splearn.domain.MemberFixture;
import wonspring.splearn.domain.PasswordEncoder;

@TestConfiguration
public class SplearnTestConfiguration {

    @Bean
    public EmailSender emailSender() {
        return (email, subject, body) -> System.out.println("Sending email: " + email);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return MemberFixture.createPasswordEncoder();
    }
}
