package br.com.lflima.simplelogger.controllers;

import br.com.lflima.simplelogger.controllers.payloads.CustomHttpHeaders;
import br.com.lflima.simplelogger.controllers.payloads.StockRequest;
import br.com.lflima.simplelogger.controllers.payloads.StockResponse;
import br.com.lflima.simplelogger.loggers.LoggerFields;
import br.com.lflima.simplelogger.services.ServiceResponseStatus;
import br.com.lflima.simplelogger.services.StockService;
import br.com.lflima.simplelogger.services.dtos.CreateNewStockDTO;
import br.com.lflima.simplelogger.services.dtos.CreatedNewStockDTO;
import br.com.lflima.simplelogger.services.dtos.GetTickerDTO;
import br.com.lflima.simplelogger.services.dtos.GotStockDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor
public class StockMarket {

    private final StockService stockService;

    @GetMapping(path = "stocks/{ticker}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockResponse> getStock(@PathVariable String ticker, @RequestHeader(value = "correlationID", required = false) UUID correlationID, @RequestHeader(value = "transactionID", required = false) UUID transactionID) throws JsonProcessingException {

        GotStockDTO gotStockDTO = this.stockService.getStock(new GetTickerDTO(this.populateCorrelation(), this.populateTransaction(), ticker));
        HttpHeaders headers = this.getHeaders(gotStockDTO.getCorrelationID(), gotStockDTO.getTransactionID());
        if (!gotStockDTO.getServiceResponseStatus().equals(ServiceResponseStatus.SUCCESS))
            return new ResponseEntity<>(StockResponse.builder().message(gotStockDTO.getMessage()).build(), headers, gotStockDTO.getHttpStatus());

        StockResponse stockResponse = StockResponse.builder()
                    .ticker(gotStockDTO.getTicker())
                    .name(gotStockDTO.getEnterprise())
                    .price(gotStockDTO.getPrice())
                    .quantity(gotStockDTO.getQuantity())
                    .createdAt(gotStockDTO.getCreatedAt())
                    .updatedAt(gotStockDTO.getUpdatedAt())
                    .build();

        return new ResponseEntity<>(stockResponse, headers, gotStockDTO.getHttpStatus());
    }

    @PostMapping(path = "stock/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockResponse> createStock(@RequestBody StockRequest stock, @RequestHeader(value = "correlationID", required = false) UUID correlationID, @RequestHeader(value = "transactionID", required = false) UUID transactionID) {

        CreatedNewStockDTO createdStock = this.stockService.createNewStock(new CreateNewStockDTO(this.populateCorrelation(), this.populateTransaction(), stock.getName(), stock.getTicker(), stock.getQuantity(), stock.getPrice()));
        if (!createdStock.getServiceResponseStatus().equals(ServiceResponseStatus.SUCCESS))
            return new ResponseEntity<>(StockResponse.builder().message(createdStock.getMessage()).build(), this.getHeaders(createdStock.getCorrelationID(), createdStock.getTransactionID()), createdStock.getHttpStatus());

        StockResponse stockResponse = StockResponse.builder()
                .ticker(createdStock.getTicker())
                .createdAt(createdStock.getCreateAt())
                .build();

        return new ResponseEntity<>(stockResponse, this.getHeaders(createdStock.getCorrelationID(), createdStock.getTransactionID()), createdStock.getHttpStatus());
    }

    private HttpHeaders getHeaders(UUID correlationID, UUID transactionID) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(CustomHttpHeaders.CORRELATION_ID.getName(), correlationID.toString());
        headers.add(CustomHttpHeaders.TRANSACTION_ID.getName(), transactionID.toString());

        return headers;
    }

/*    FIXME CORRETO SERIA FAZER UM INTERCEPTOR NA REQUISICAO PARA DEFINIR O CORRELATION E O TRANSACTION, CASO NAO SEJAM FORNECIDOS.
       ATUALMENTE SAO CRIADOS AO LOGAR A REQUISICAO POR PRATICIDADE */
    private UUID populateTransaction() {

        return UUID.fromString(ThreadContext.get(LoggerFields.TRANID.getName()));
    }
    private UUID populateCorrelation() {
        return UUID.fromString(ThreadContext.get(LoggerFields.CORRID.getName()));
    }
}