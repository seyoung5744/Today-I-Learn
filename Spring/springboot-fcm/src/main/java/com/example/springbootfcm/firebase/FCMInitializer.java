package com.example.springbootfcm.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FCMInitializer {

    @Value("${fcm.certification}")
    private String googleAppCredentials;

    @PostConstruct
    public void initialize() throws IOException {
        log.info("구글{}",googleAppCredentials);
        ClassPathResource resource = new ClassPathResource(googleAppCredentials);

        try(InputStream input = resource.getInputStream()){
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(input))
                .build();

            if(FirebaseApp.getApps().isEmpty()){
                FirebaseApp.initializeApp(options);
                log.info("FirebaseApp initialization complete");
            }
        }
    }
}
