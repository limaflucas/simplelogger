package br.com.lflima.simplelogger.controllers.payloads;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomHttpHeaders {

    CORRELATION_ID("CorrelationID"),
    TRANSACTION_ID("TransactionID");

    private final String name;
}
