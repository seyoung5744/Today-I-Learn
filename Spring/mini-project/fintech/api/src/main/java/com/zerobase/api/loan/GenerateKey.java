package com.zerobase.api.loan;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class GenerateKey {

    public String generateUserKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
