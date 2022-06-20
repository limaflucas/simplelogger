package br.com.lflima.simplelogger.controllers;

import br.com.lflima.simplelogger.controllers.payloads.CustomHttpHeaders;
import br.com.lflima.simplelogger.services.ports.StockPort;
import br.com.lflima.simplelogger.services.ports.dtos.GotStockPortDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class StockPortImpl implements StockPort {

    private final ObjectMapper objectMapper;

    @Override
    public GotStockPortDTO getByTicker(String ticker, UUID correlationHeader, UUID transactionHeader) {

        try {
            HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(HttpRequest.newBuilder()
                            .uri(new URI("http://localhost:8080/stocks/" + ticker))
                            .GET()
                            .header(CustomHttpHeaders.CORRELATION_ID.getName(), correlationHeader.toString())
                            .header(CustomHttpHeaders.TRANSACTION_ID.getName(), transactionHeader.toString())
                            .build(), HttpResponse.BodyHandlers.ofString());
            return this.objectMapper.readValue(response.body(), GotStockPortDTO.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            log.error("Impossible to build a request to getByTicker");
            throw new RuntimeException(e);
        }
    }
}

