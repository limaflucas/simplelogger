package br.com.lflima.simplelogger.loggers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoggerFields {

    TRANID("transaction_id"),
    CORRID("correlation_id"),
    NOW("timestamp"),
    DATA("data");

    private final String name;
}
