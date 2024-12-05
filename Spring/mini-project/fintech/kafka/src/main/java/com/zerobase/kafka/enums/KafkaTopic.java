package com.zerobase.kafka.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KafkaTopic {

    LOAN_REQUEST("loan_request");

    private final String topicName;
}
