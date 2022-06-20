package br.com.lflima.simplelogger.config;

import br.com.lflima.simplelogger.controllers.payloads.CustomHttpHeaders;
import br.com.lflima.simplelogger.loggers.LoggerFields;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.zalando.logbook.*;
import org.zalando.logbook.json.JsonHttpLogFormatter;

import java.io.IOException;
import java.util.UUID;

;

@Component
@RequiredArgsConstructor
public class LogbookConfig {
    private final ObjectMapper objectMapper;

    @Bean public Logbook configuraLogbook() {
        return Logbook.builder()
                .correlationId(request ->request.getHeaders().getFirst(CustomHttpHeaders.CORRELATION_ID.getName()) == null ? UUID.randomUUID().toString() : request.getHeaders().getFirst(CustomHttpHeaders.CORRELATION_ID.getName()))
                .sink(new DefaultSink(
                        new JsonHttpLogFormatter(),
                        new LogbookCustomLogWriter(this.objectMapper)))
                .build();
    }
}
@Log4j2
@Component
@RequiredArgsConstructor
class LogbookCustomLogWriter implements HttpLogWriter {

    private static final LogbookFields CORRELATION = LogbookFields.CORRELATION_ID;
    private static final LogbookFields TRANSACTION = LogbookFields.TRANSACTION_ID;
    private static final int BEGIN_INDEX = 2;
    private static final int END_INDEX = 38;
    private final ObjectMapper objectMapper;

    @Override
    public void write(Precorrelation precorrelation, String request) throws IOException {

        JsonNode jsonNode = this.objectMapper.readTree(request);
        if (ThreadContext.get(LoggerFields.CORRID.getName()) == null)
            if (jsonNode.findValue(CORRELATION.getName()) == null)
                ThreadContext.put(LoggerFields.CORRID.getName(), precorrelation.getId());
            else
                ThreadContext.put(LoggerFields.CORRID.getName(), jsonNode.findValue(CORRELATION.getName()).toString().substring(BEGIN_INDEX, END_INDEX));

        if (ThreadContext.get(LoggerFields.TRANID.getName()) == null)
            if (jsonNode.findValue(TRANSACTION.getName()) == null)
                ThreadContext.put(LoggerFields.TRANID.getName(), UUID.randomUUID().toString());
            else
                ThreadContext.put(LoggerFields.TRANID.getName(), jsonNode.findValue(TRANSACTION.getName()).toString().substring(BEGIN_INDEX, END_INDEX));

        ThreadContext.put(LoggerFields.DATA.getName(), request);
        log.info("REQUEST RECEIVED");
        ThreadContext.remove(LoggerFields.DATA.getName());
    }

    @Override
    public void write(Correlation correlation, String response) throws IOException {

        ThreadContext.put(LoggerFields.DATA.getName(), response);
        log.info("RESPONSE SENT");
        ThreadContext.clearAll();
    }
}

@Getter
@RequiredArgsConstructor
enum LogbookFields {

    CORRELATION_ID("correlationid"),
    TRANSACTION_ID("transactionid");

    private final String name;
}