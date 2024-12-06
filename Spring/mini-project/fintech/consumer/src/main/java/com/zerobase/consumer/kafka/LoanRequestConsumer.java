package com.zerobase.consumer.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.consumer.service.LoanRequestService;
import com.zerobase.kafka.dto.LoanRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanRequestConsumer {

    private final ObjectMapper objectMapper;
    private final LoanRequestService loanRequestService;

    @KafkaListener(topics = {"loan_request"}, groupId = "fintech")
    public void loanRequestTopicConsumer(String message) {
        try {
            LoanRequestDto loanRequestKafkaDto = objectMapper.readValue(message, LoanRequestDto.class);

            loanRequestService.loanRequest(loanRequestKafkaDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
